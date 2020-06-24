package estrategias.exportacion.excel;

import config.Config;
import estrategias.exportacion.EstrategiaDeExportacion;
import exportables.Exportable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExportarAExcel implements EstrategiaDeExportacion {
    private XSSFWorkbook workbook;
    private String nombreDeArchivo;

    public ExportarAExcel(String nombreDeArchivo) {
        this.nombreDeArchivo = nombreDeArchivo;
    }

    public String exportar(Exportable exportable) {
        crearLibroDeTrabajo();
        XSSFSheet hoja = crearHoja("Hoja1");
        agregarDatos(hoja, exportable.datos());
        guardar();
        return rutaCompletaDelArchivo();
    }

    private String rutaCompletaDelArchivo(){
        return Config.RUTA_EXPORTACION + this.nombreDeArchivo;
    }

    private void guardar(){
        try {
            FileOutputStream outputStream = new FileOutputStream(rutaCompletaDelArchivo());
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearLibroDeTrabajo(){
        this.workbook = new XSSFWorkbook();
    }

    private XSSFSheet crearHoja(String nombre){
        return workbook.createSheet(nombre);
    }

    private void agregarDatos(XSSFSheet hoja, Map<String, List<String>> datos){
        int numeroDeFila = 0;

        for (Map.Entry<String, List<String>> entry : datos.entrySet()) {
            List<String> valores = entry.getValue();
            Row fila = crearFila(hoja, numeroDeFila);
            numeroDeFila++;
            agregarColumna(valores, fila, hoja);
        }
    }
    private Row crearFila(XSSFSheet hoja, int numeroDeFila){
        return hoja.createRow(numeroDeFila);
    }

    private void agregarColumna(List<String> valores, Row fila, XSSFSheet hoja){
        int numeroDeColumna = 0;
        for (String v : valores) {
            Cell celda = fila.createCell(numeroDeColumna);
            numeroDeColumna++;
            celda.setCellValue(v);
        }
    }
}