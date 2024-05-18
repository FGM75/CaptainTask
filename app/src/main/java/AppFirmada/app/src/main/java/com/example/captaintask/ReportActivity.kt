package com.example.captaintask
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.echo.holographlibrary.Bar
import com.echo.holographlibrary.BarGraph
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ReportActivity : AppCompatActivity() {

    private lateinit var dataTypeSpinner: Spinner
    private lateinit var graphLayout: LinearLayout
    private lateinit var notesEditText: EditText

    private val WRITE_EXTERNAL_STORAGE_REQUEST_PDF = 102
    private val WRITE_EXTERNAL_STORAGE_REQUEST_EXCEL = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        dataTypeSpinner = findViewById(R.id.data_type_spinner)
        graphLayout = findViewById(R.id.graph_layout)
        notesEditText = findViewById(R.id.notes_edit_text)

        val generateReportButton = findViewById<Button>(R.id.generate_report_button)
        generateReportButton.setOnClickListener { generateReport() }

        val sendReportButton = findViewById<Button>(R.id.send_report_button)
        sendReportButton.setOnClickListener { sendReport() }

        val exportPdfButton = findViewById<Button>(R.id.export_pdf_button)
        exportPdfButton.setOnClickListener { checkStoragePermissionAndExportPdf() }

        val exportExcelButton = findViewById<Button>(R.id.export_excel_button)
        exportExcelButton.setOnClickListener { checkStoragePermissionAndExportExcel() }

        setupSpinner()
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.data_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataTypeSpinner.adapter = adapter
        }
    }

    private fun generateReport() {
        val selectedDataType = dataTypeSpinner.selectedItem.toString()

        // Generar datos de muestra para el gráfico dependiendo de la opción seleccionada
        val bars: ArrayList<Bar> = when (selectedDataType) {
            "Clientes" -> generateClientData()
            "Ventas" -> generateSalesData()
            "Inventario" -> generateInventoryData()
            else -> ArrayList() // En caso de no seleccionar ninguna opción válida
        }
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.bar_color_1),
            ContextCompat.getColor(this, R.color.bar_color_2),
            ContextCompat.getColor(this, R.color.bar_color_3),
            ContextCompat.getColor(this, R.color.bar_color_4),
            ContextCompat.getColor(this, R.color.bar_color_5),
            ContextCompat.getColor(this, R.color.bar_color_6),
            ContextCompat.getColor(this, R.color.bar_color_7),
            ContextCompat.getColor(this, R.color.bar_color_8),
            ContextCompat.getColor(this, R.color.bar_color_9),
            ContextCompat.getColor(this, R.color.bar_color_10),
            // Añade más colores según sea necesario
        )


        // Crear y configurar el gráfico de barras
        val barGraph = BarGraph(this)
        bars.forEachIndexed { index, bar ->
            if (index < colors.size) {
                bar.color = colors[index]
            }
        }
        barGraph.setBars(bars)

        // Limpiar el diseño del gráfico y agregar el nuevo gráfico
        graphLayout.removeAllViews()
        graphLayout.addView(barGraph)

        // Mostrar un mensaje de éxito
        Toast.makeText(this, "Informe generado correctamente para $selectedDataType", Toast.LENGTH_SHORT).show()
    }

    private fun generateClientData(): ArrayList<Bar> {
        val bars: ArrayList<Bar> = ArrayList()
        bars.add(Bar().apply {
            name = "Maria"
            value = 15f
        })
        bars.add(Bar().apply {
            name = "Jose"
            value = 25f
        })
        bars.add(Bar().apply {
            name = "Felipe"
            value = 20f
        })
        bars.add(Bar().apply {
            name = "Carlos"
            value = 30f
        })
        return bars
    }

    private fun generateSalesData(): ArrayList<Bar> {
        val bars: ArrayList<Bar> = ArrayList()
        bars.add(Bar().apply {
            name = "Enero"
            value = 10f
        })
        bars.add(Bar().apply {
            name = "Febrero"
            value = 20f
        })
        bars.add(Bar().apply {
            name = "Marzo"
            value = 15f
        })
        return bars
    }

    private fun generateInventoryData(): ArrayList<Bar> {
        val bars: ArrayList<Bar> = ArrayList()
        bars.add(Bar().apply {
            name = "Pasta"
            value = 50f
        })
        bars.add(Bar().apply {
            name = "Agua"
            value = 30f
        })
        bars.add(Bar().apply {
            name = "Tomate"
            value = 40f
        })
        bars.add(Bar().apply {
            name = "Arroz"
            value = 60f
        })
        bars.add(Bar().apply {
            name = "Leche"
            value = 20f
        })
        bars.add(Bar().apply {
            name = "Pan"
            value = 35f
        })
        bars.add(Bar().apply {
            name = "Huevos"
            value = 45f
        })
        bars.add(Bar().apply {
            name = "Manzanas"
            value = 55f
        })

        return bars
    }

    private fun sendReport() {
        val notes = notesEditText.text.toString()
        // Implementa la lógica para enviar el informe aquí, por ejemplo, enviar por correo electrónico
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Informe generado")
        emailIntent.putExtra(Intent.EXTRA_TEXT, notes)
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar informe por correo electrónico..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No hay clientes de correo electrónico instalados.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkStoragePermissionAndExportPdf() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permiso
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST_PDF
            )
        } else {
            // Permiso ya concedido, proceder con la exportación a PDF
            exportToPdf()
        }
    }

    private fun checkStoragePermissionAndExportExcel() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permiso
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST_EXCEL
            )
        } else {
            // Permiso ya concedido, proceder con la exportación a Excel
            exportToExcel()
        }
    }

    private fun exportToPdf() {
        val document = Document()
        try {
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "informe.pdf")
            val fileOutputStream = FileOutputStream(file)
            PdfWriter.getInstance(document, fileOutputStream)

            document.open()
            document.add(Paragraph("Contenido del informe...")) // Agregar contenido al PDF aquí
            document.close()

            // Escanear el archivo para que aparezca en la galería o lista de descargas
            MediaScannerConnection.scanFile(
                this,
                arrayOf(file.toString()),
                arrayOf("application/pdf"),
                null
            )

            Toast.makeText(this, "Informe exportado correctamente como PDF", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al exportar el informe como PDF", Toast.LENGTH_SHORT).show()
        }
    }


    private fun exportToExcel() {
        val notes = notesEditText.text.toString()

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Informe")
        val row = sheet.createRow(0)
        val cell = row.createCell(0)
        cell.setCellValue(notes)

        try {
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "informe.xlsx")
            val fileOutputStream = FileOutputStream(file)
            workbook.write(fileOutputStream)
            fileOutputStream.close()

            // Escanear el archivo para que aparezca en la galería o lista de descargas
            MediaScannerConnection.scanFile(
                this,
                arrayOf(file.toString()),
                arrayOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                null
            )

            Toast.makeText(this, "Informe exportado a Excel correctamente", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al exportar el informe a Excel", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_PDF) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, proceder con la exportación a PDF
                exportToPdf()
            } else {
                // Permiso denegado, mostrar un mensaje al usuario o tomar alguna otra acción
                Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_EXCEL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, proceder con la exportación a Excel
                exportToExcel()
            } else {
                // Permiso denegado, mostrar un mensaje al usuario o tomar alguna otra acción
                Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
