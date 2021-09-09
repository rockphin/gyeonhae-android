package us.gijuno.gyeonhae.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.googlecode.tesseract.android.TessBaseAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import us.gijuno.gyeonhae.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TextActivity : AppCompatActivity() {

    lateinit var tess: TessBaseAPI //Tesseract API 객체 생성
    var dataPath: String = "" //데이터 경로 변수 선언
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview: Preview
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        dataPath = filesDir.toString() + "/tesseract/" //언어데이터의 경로 미리 지정

        checkFile(File(dataPath + "tessdata/"), "kor") //사용할 언어파일의 이름 지정
        checkFile(File(dataPath + "tessdata/"), "eng")

        val lang: String = "kor+eng"
        tess = TessBaseAPI() //api준비
        tess.init(dataPath, lang) //해당 사용할 언어데이터로 초기화

        preview = Preview.Builder()
            .build()
            .also { it.setSurfaceProvider(findViewById<PreviewView>(R.id.textViewFinder).surfaceProvider) }

        cameraExecutor = Executors.newSingleThreadExecutor()

        outputDirectory = getOutputDirectory()

        findViewById<ImageView>(R.id.text_take_image_btn).setOnClickListener { takePhoto() }

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun copyFile(lang: String) {
        try {
            //언어데이타파일의 위치
            val filePath: String = dataPath + "/tessdata/" + lang + ".traineddata"

            //AssetManager 를 사용하기 위한 객체 생성
            val assetManager: AssetManager = getAssets();

            //byte 스트림을 읽기 쓰기용으로 열기
            val inputStream: InputStream = assetManager.open("tessdata/" + lang + ".traineddata")
            val outStream: OutputStream = FileOutputStream(filePath)

            //위에 적어둔 파일 경로쪽으로 해당 바이트코드 파일을 복사한다.
            val buffer: ByteArray = ByteArray(1024)

            var read: Int = 0
            read = inputStream.read(buffer)
            while (read != -1) {
                outStream.write(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            outStream.flush()
            outStream.close()
            inputStream.close()
        } catch (e: FileNotFoundException) {
            Log.e("오류발생", e.toString())
        } catch (e: IOException) {
            Log.e("오류발생", e.toString())
        }
    }

    private fun checkFile(dir: File, lang: String) {

        //파일의 존재여부 확인 후 내부로 복사
        if (!dir.exists() && dir.mkdirs()) {
            copyFile(lang)
        }

        if (dir.exists()) {
            val datafilePath: String = dataPath + "/tessdata/" + lang + ".traineddata"
            val dataFile: File = File(datafilePath)
            if (!dataFile.exists()) {
                copyFile(lang)
            }
        }
    }

    private fun processImage(bitmap: Bitmap) {
        findViewById<ImageView>(R.id.test_imageview).setImageBitmap(bitmap)
        GlobalScope.launch {
            Log.d("tesseract", bitmap.toString())
            Log.d("tesseract", "잠시 기다려주세요")
            var ocrResult: String? = null
            Log.d("tesseract", "잠시 기다려주세요")
            tess.setImage(bitmap)
            Log.d("tesseract", "잠시 기다려주세요")
            ocrResult = tess.utF8Text
            Log.d("tesseract", "잠시 기다려주세요")
            Log.d("tesseract", ocrResult)

            withContext(Dispatchers.Main) {
                onOCRComplete(tess.utF8Text)
            }
        }
    }

    private fun onOCRComplete(text: String) {
        findViewById<TextView>(R.id.text_result).text = text
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA
            ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    processImage(BitmapFactory.decodeStream(contentResolver.openInputStream(savedUri))) //이미지 가공후 텍스트뷰에 띄우기
//                    processImage(BitmapFactory.decodeResource(resources,R.drawable.text)) //이미지 가공후 텍스트뷰에 띄우기

                }
            }
        )

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun stopCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            cameraProvider.unbind(preview)
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray,
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}