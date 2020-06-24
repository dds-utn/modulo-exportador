package exportador;

import config.Config;
import estrategias.exportacion.excel.ExportarAExcel;
import estrategias.exportacion.pdf.AdapterApachePDFBox;
import estrategias.exportacion.pdf.ExportarAPDF;
import exportables.Documento;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExportadorTest {
    private Exportador exportador;
    private Documento documento;

    @Before
    public void init(){
        this.exportador = new Exportador(new ExportarAExcel("datos.xlsx"));
        Documento documento  = new Documento();
        documento.agregarDato("0", "Nombre", "Apellido", "Edad");
        this.documento = documento;
    }

    @Test
    public void exportarAExcelGeneraArchivo(){
        this.documento.agregarDato("1", "Ezequiel", "Escobar", "24");
        this.documento.agregarDato("2", "Lucas", "Saclier", "42");
        this.exportador.setExportable(this.documento);
        Assert.assertEquals(Config.RUTA_EXPORTACION + "datos.xlsx", this.exportador.exportar());
    }

    @Test
    public void exportarAPDFGeneraArchivo(){
        this.documento.agregarDato("1", "Ezequiel", "Escobar", "24");
        this.documento.agregarDato("2", "Lucas", "Saclier", "33");
        this.exportador.setEstrategia(new ExportarAPDF(new AdapterApachePDFBox("datos.pdf")));
        this.exportador.setExportable(this.documento);
        Assert.assertEquals(Config.RUTA_EXPORTACION + "datos.pdf", this.exportador.exportar());
    }
}