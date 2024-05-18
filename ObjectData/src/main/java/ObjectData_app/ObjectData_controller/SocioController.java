package ObjectData_app.ObjectData_controller;

import ObjectData_app.ObjectData_model.SocioEstandarModel;
import ObjectData_app.ObjectData_model.SocioFederadoModel;
import ObjectData_app.ObjectData_model.SocioInfantilModel;
import ObjectData_app.ObjectData_model.SocioModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import ObjectData_app.ObjectData_view.NotificacionView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import ObjectData_app.ObjectData_model.ExcursionModel;
import ObjectData_app.ObjectData_model.FederacionModel;
import ObjectData_app.ObjectData_model.InscripcionModel;
import ObjectData_app.ObjectData_model.SeguroModel;
import ObjectData_app.ObjectData_model.SeguroModel.TipoSeguro;
//Se añaden las vistas necesarias.
import ObjectData_app.ObjectData_controller.SocioController;

public class SocioController {
    // Propiedades
    @FXML
    private Text tInfo;
    @FXML
    private Text tInfo1;
    @FXML
    private CheckBox filterEstandar;
    @FXML
    private CheckBox filterFederado;
    @FXML
    private CheckBox filterInfantil;
    @FXML
    private TextField filterNumeroSocio;
    @FXML
    private FilteredList<Object> filteredData;
    @FXML
    private TableView<Object> taTodosLosSocios;
    @FXML
    private TableColumn<Object, Integer> taNumeroSocio;
    @FXML
    private TableColumn<Object, String> taNombre;
    @FXML
    private TableView<Object> taResultadoFacturacion;
    @FXML
    private TableColumn<Object, String> taConceptoFacturacion;
    @FXML
    private TableColumn<Object, String> taCosteFacturacion;
    @FXML
    private TextField tfNumeroSocio;
    @FXML
    private TableColumn<Object, Integer> colNumeroSocio;
    @FXML
    private TableColumn<Object, String> colNombre;
    // FIN

    //Componentes de 'Socio Federado'
    @FXML
    private TextField tfNombreSocioFederado;
    @FXML
    private TextField tfDniSocioFederado;
    @FXML
    private Button btCrearSocioFederado;
    @FXML 
    private ComboBox<String> cbFederaciones;

    //Componentes de 'Socio Infantil'
    @FXML
    private TextField tfNombreSocioInfantil;
    

    @FXML
   public void initialize() {
        cargarFederaciones();
        if(cbTipoSeguro!=null) cbTipoSeguro.getItems().addAll("1 - Básico", "2 - Completo");

        // Configurar las columnas de la tabla de eliminación de socios
        if (taNumeroSocio != null)
            taNumeroSocio.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        if (taNombre != null)
            taNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @FXML
    public void initializeForm() {
        if (cbTipoSeguro != null)
            cbTipoSeguro.getItems().addAll("1 - Básico", "2 - Completo");
        limpiarAvisoEliminacion();
    }

    public void inicializarScreenEliminacion() {
        taTodosLosSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(newSelection);
            }
        });
        limpiarAvisoEliminacion();
        // Configurar las columnas de la tabla de eliminación de socios
        if (taNumeroSocio != null)
            taNumeroSocio.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        if (taNombre != null)
            taNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void applyFilter() {
        filteredData.setPredicate(item -> {
            boolean estandar = item instanceof SocioEstandarModel;
            boolean federado = item instanceof SocioFederadoModel;
            boolean infantil = item instanceof SocioInfantilModel;

            return (filterEstandar.isSelected() && estandar) ||
                    (filterFederado.isSelected() && federado) ||
                    (filterInfantil.isSelected() && infantil);
        });
    }

    // SocioEstandar:
    @FXML
    private TextField tfNombreSocioEstandar;
    @FXML
    private TextField tfDniSocioEstandar;
    @FXML
    private ChoiceBox<String> cbTipoSeguro;
    @FXML
    private Button btCrear;

    // Método para generar un número de socio aleatorio
    public static int generarID() {
        Random rand = new Random();
        int id = 0;
        for (int i = 0; i < 8; i++) {
            id = id * 10 + rand.nextInt(9) + 1;
        }
        if (id < 0) {
            return id * -1;
        }
        return id;
    }

    // Metodo para añadir un socio estandar
    @FXML
    public void crearSocioEstandar() {
        // Atributos
        String nombre = tfNombreSocioEstandar.getText();
        String NIF = tfDniSocioEstandar.getText();
        String tipoSeguroSeleccionado = cbTipoSeguro.getValue();
        if (nombre.isEmpty() || NIF.isEmpty() || tipoSeguroSeleccionado == null) {
            NotificacionView.Notificacion("WARNING", "Campos Vacíos", "Por favor, completa todos los campos.");
            return;
        }
        int numeroSocio;
        boolean todoOk = false;
        do {
            numeroSocio = Integer.parseInt("5" + generarID());
            SeguroModel seguroModel = seguroSocio();
            if (seguroModel == null) {
                NotificacionView.Notificacion("ERROR", "Tipo de Seguro Inválido",
                        "Por favor, selecciona un tipo de seguro válido.");
                break;
            }
            SocioEstandarModel socioModel = new SocioEstandarModel(numeroSocio, nombre, NIF, seguroModel);
            try {
                SocioEstandarModel.crearSocioEstandar(socioModel);
                NotificacionView.Notificacion("INFORMATION", "Éxito", "Se ha creado el socio estándar correctamente.");
                todoOk = true;
            } catch (Exception e) {
                NotificacionView.Notificacion("ERROR", "Error en la Creación",
                        "Hubo un error al crear el socio estándar: " + e.getMessage());
            }
        } while (!todoOk);
    }

    public void modificarSeguroSocioEstandar() {
        // Atributos
        String nombre = tfNombreSocioEstandar.getText();
        String NIF = tfDniSocioEstandar.getText();
        String tipoSeguroSeleccionado = cbTipoSeguro.getValue();

        if (nombre.isEmpty() || NIF.isEmpty() || tipoSeguroSeleccionado == null) {
            NotificacionView.Notificacion("WARNING", "Campos Vacíos", "Por favor, completa todos los campos.");
            return;
        }
        int numeroSocio;
        boolean todoOk = false;
        // Imprimitos el titulo de la función.
        // RespView.tituloDeLaFuncion("-- FORMULARIO PARA MODIFICAR EL TIPO DE SEGURO
        // --");
        // Bucle de logica para comprobar datos.
        do {
            // Pedimos el nombre del socio.
            retorno = SociView.obtenerNumeroSocio();
            // Si retorno esta vacio salimos.
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            } else if (retorno.matches("\\d+")) { // Verifica si el retorno es un número entero
                numeroSocio = Integer.parseInt(retorno);
                todoOk = true;
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("El número de socio debe ser un numero.");
                continue;
            }
            // Obtenemos el objeto socio estandar (si existe)
            try {
                socio = SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio);
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            if (socio != null) {
                SeguroModel seguroModel = seguroSocio();
                try {
                    socio.actualizarSeguroSocioEstandar(seguroModel, socio);
                    RespView.respuestaControllerView("El socio se ha actualizado correctamente.");
                    todoOk = true;
                } catch (Exception e) {
                    RespView.respuestaControllerView(e.getMessage());
                }
            } else {
                RespView.excepcionesControllerView("No se ha podido encontrar el socio.");
                continue;
            }
        } while (!todoOk);
    }

    @FXML
private void crearSocioFederado() {
    String nombre = tfNombreSocioFederado.getText();
    String NIF = tfDniSocioFederado.getText();
    int numeroSocio;
    FederacionModel federacion = null;

    if (nombre.isEmpty() || NIF.isEmpty()) {
        NotificacionView.Notificacion("ERROR", "Campos vacíos", "Por favor, llene todos los campos.");
        return;
    }

    String seleccion = cbFederaciones.getValue(); // Obtener el valor seleccionado del ComboBox

    if (seleccion == null) {
        NotificacionView.Notificacion("ERROR", "Federación no seleccionada", "Por favor, seleccione una federación.");
        return;
    }

    try {
        // Dividir la selección por el carácter "-"
        String[] partes = seleccion.split("-");
        // Tomar la primera parte, que debería ser el ID
        String idFederacion = partes[0].trim();
        // Obtener la federación utilizando el ID
        federacion = FederacionModel.obtenerFederacion(Integer.valueOf(idFederacion));
    } catch (Exception e) {
        NotificacionView.Notificacion("ERROR", "Error al obtener federación", e.getMessage());
        return;
    }

    // Generar el número de socio y mostrar notificación
    numeroSocio = Integer.parseInt("6" + generarID());
    
    // Crear el objeto SocioFederadoModel
    SocioFederadoModel socio = new SocioFederadoModel(numeroSocio, nombre, NIF, federacion.getCodigo());

    try {
        socio.crearSocioFederado(socio);
        NotificacionView.Notificacion("INFORMATION", "Socio creado", "Se ha creado el socio federado correctamente.");
    } catch (Exception e) {
        NotificacionView.Notificacion("ERROR", "Error al crear socio", e.getMessage());
    }
}

    

    private void cargarFederaciones() {
        if(cbFederaciones==null) return;
        String[] listaFederaciones;

        try {
            listaFederaciones = FederacionModel.obtenerListadoFederacion();
            String listado = listaFederaciones[0];
            String[] federaciones = listado.split("\n");

            // Crear una lista observable y agregar las federaciones
            ObservableList<String> observableFederaciones = FXCollections.observableArrayList();

            for (String federacion : federaciones) {
                if (!federacion.trim().isEmpty() && !federacion.equals("- Sin datos.")) {
                    observableFederaciones.add(federacion.trim());
                }
            }

            // Establecer la lista observable en el ComboBox
            cbFederaciones.setItems(observableFederaciones);

        } catch (Exception e) {
            NotificacionView.Notificacion("ERROR", "Error al cargar federaciones", e.getMessage());
        }
    }

    @FXML
    public void crearSocioInfantil() {
        // Atributos
        String nombre;
        
        // Obtener el socio parental seleccionado
        SocioModel socioSeleccionado = (SocioModel) taTodosLosSocios.getSelectionModel().getSelectedItem();
        
        // Verificar si se seleccionó un socio
        if (socioSeleccionado == null) {
            NotificacionView.Notificacion("WARNING", "Atención", "Debe seleccionar un socio parental");
            return;
        }
    
        // Obtener el nombre del socio infantil
        nombre = tfNombreSocioInfantil.getText();
    
        // Verificar si se ingresó un nombre
        if (nombre.isEmpty()) {
            NotificacionView.Notificacion("WARNING", "Atención", "Debe indicar el nombre del socio infantil");
            return;
        }
    
        // Generar el número de socio y mostrar notificación
        int numeroSocio = Integer.parseInt("7" + generarID()); // Número de socio
    
        // Crear el objeto SocioInfantilModel
        SocioInfantilModel socioInfantil = new SocioInfantilModel(numeroSocio, nombre, socioSeleccionado.getNumeroSocio());
    
        // Enviamos la información al modelo para que añada el socio a la BBDD
        try {
            socioInfantil.crearSocioInfantil(socioInfantil);
            NotificacionView.Notificacion("INFORMATION", "", "Se ha creado el socio infantil " + numeroSocio);
        } catch (Exception e) {
            NotificacionView.Notificacion("ERROR", "Error al crear socio infantil", "Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    
    

    

    private void mostrarAvisoEliminacion(String warning) {
        tInfo.setText(warning);
    }

    private void limpiarAvisoEliminacion() {
        tInfo.setText("");
    }

    @FXML
    public void accionEliminarSocio() {
        boolean inscritoEnExcursion = false;
        taTodosLosSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int numeroSocio = ((SocioModel) newSelection).getNumeroSocio();
                String tipoSocio = SocioModel.obtenerTipoSocioPorNumeroSocio(numeroSocio);
                // Eliminar el socio según su tipo
                try {
                    // try {
                    // inscritoEnExcursion = InscripcionModel.comprobarSocioInscrito(numeroSocio);
                    // } catch (Exception e) {
                    // mostrarAvisoEliminacion("Ha ocurido un error en la elimicación. Causa:" +
                    // e.getMessage());
                    // return;
                    // }
                    // // Verificar si el socio está inscrito en alguna excursión
                    // try {
                    // inscritoEnExcursion = InscripcionModel.comprobarSocioInscrito(numeroSocio);
                    // } catch (Exception e) {
                    // mostrarAvisoEliminacion("Ha ocurido un error en la elimicación. Causa:" +
                    // e.getMessage());
                    // return;
                    // }
                    // if (inscritoEnExcursion) {
                    // // Mostrar mensaje de que el socio está inscrito en una excursión y no puede
                    // ser
                    // // eliminado
                    // mostrarAvisoEliminacion("El socio con número de socio " + numeroSocio
                    // + " está inscrito en una excursión y no puede ser eliminado.");
                    // return;
                    // }
                    Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
                    alertConfirmation.setTitle("Confirmación de Eliminación");
                    alertConfirmation.setHeaderText(null);
                    alertConfirmation.setContentText("¿Estás seguro de que deseas eliminar al socio con número de socio " + numeroSocio + "?");
                    Optional<ButtonType> result = alertConfirmation.showAndWait();
                    if (result.isPresent() && result.get() != ButtonType.OK) {
                        return;
                    }
                    switch (tipoSocio) {
                        case "Estandar":
                            SocioEstandarModel.eliminarSocioModel(numeroSocio);
                            break;
                        case "Federado":
                            SocioFederadoModel.eliminarSocioModel(numeroSocio);
                            break;
                        case "Infantil":
                            SocioInfantilModel.eliminarSocioModel(numeroSocio);
                            break;
                        default:
                            throw new Exception("No se ha podido identificar el tipo de socio.");
                    }
                } catch (Exception e) {
                    mostrarAvisoEliminacion("Ha ocurido un error en la elimicación. Causa:" + e.getMessage());
                    System.err.println("Ha ocurido un error en la elimicación. Causa:" + e.getMessage());
                }
            }
        });
    }

    // Metodo para seleccionar el seguro de un socio estandar.
    @FXML
    private SeguroModel seguroSocio() {
        // Tratamiento del seguro
        TipoSeguro tipoSeguro = null;
        String retornoSeguro = cbTipoSeguro.getValue();
        if (retornoSeguro.startsWith("1")) {
            tipoSeguro = TipoSeguro.BASICO;
        } else if (retornoSeguro.startsWith("2")) {
            tipoSeguro = TipoSeguro.COMPLETO;
        } else {
            return null;
        }
        return new SeguroModel(tipoSeguro);
    }

    @FXML
    public void facturaMensualSocio() {
        taTodosLosSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tInfo1.setText("Calculando ...");
                // Inicializamos la tabla de facturación
                taConceptoFacturacion.setCellValueFactory(cellData -> {
                    Object item = cellData.getValue();
                    if (item instanceof String[]) {
                        String[] values = (String[]) item;
                        return new SimpleStringProperty(values[0]);
                    }
                    return null;
                });
                taCosteFacturacion.setCellValueFactory(cellData -> {
                    Object item = cellData.getValue();
                    if (item instanceof String[]) {
                        String[] values = (String[]) item;
                        return new SimpleStringProperty(values[1]);
                    }
                    return null;
                });
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() {
                        try {
                            String[] conceptoStrings = {};
                            ArrayList<String[]> conceptosArrayList = new ArrayList<>();
                            int numeroSocio = ((SocioModel) newSelection).getNumeroSocio();
                            String tipoSocio = SocioModel.obtenerTipoSocioPorNumeroSocio(numeroSocio);
                            Double facturacion = 0.0;
                            final Double cuotaMensual = 10.00;

                            if (tipoSocio.equals("Estandar")) {
                                //Cuota mensual
                                conceptoStrings = new String[] { "Cuota mensual", cuotaMensual.toString() };
                                conceptosArrayList.add(conceptoStrings);

                                //Precio seguro
                                Double precioSeguro = 0.0;
                                try {
                                    precioSeguro = SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio).getSeguro().getPrecio();
                                } catch (Exception e) {
                                    NotificacionView.Notificacion("ERROR", "Error encontrado", "Fallo en la ejecución del programa: " + e);
                                }
                                conceptoStrings = new String[] { "Seguro", precioSeguro.toString() };
                                conceptosArrayList.add(conceptoStrings);
                                //
                                taResultadoFacturacion.setItems(FXCollections.observableArrayList(conceptosArrayList));
                                // Coste de la cuota
                                // respuesta += "\n - Coste de la cuota: " + cuotaMensual + " euros.";
                                // Obtenemos el precio del seguro contratado desde el modelo.
                                // Al intentar obtener datos de un medio exteno, en este caso la BBDD, debemos
                                // usar try para comprobar posibles excepciones.

                                // Obtener listado de excursiones y precio:
                                // Al intentar obtener datos de un medio exteno, en este caso la BBDD, debemos
                                // usar try para comprobar posibles excepciones.
                                try {
                                    // = InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio);
                                } catch (Exception e) {
                                    NotificacionView.Notificacion("ERROR", "Error encontrado",
                                            "Fallo en la ejecución del programa: " + e);
                                }

                                // respuesta += retornoArray[0];

                                // // Precio del seguro.
                                // respuesta += "\n - Coste del seguro: " + precioSeguro + " euros.";
                                // // Se genera el precio final de facturación
                                // facturacion = cuotaMensual + precioSeguro +
                                // Double.parseDouble(retornoArray[1]);
                                // // Se manda el resultado a la vista
                                // respuesta += "\n El socio factura " + facturacion + " euros mensuales.";
                                // } else if (tipoSocio.equals("Federado")) {
                                // // Aplicamos un despues de la cuota mensual 5%
                                // Double precioCuotaDescuento = cuotaMensual - (cuotaMensual * 5 / 100);
                                // // Obtener listado de escursiones y precio:
                                // // Al intentar obtener datos de un medio exteno, en este caso la BBDD,
                                // debemos
                                // // usar try para comprobar posibles excepciones.
                                // try {
                                // retornoArray = InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio);
                                // } catch (Exception e) {
                                // RespView.excepcionesControllerView(e.getMessage());
                                // }

                                // respuesta += retornoArray[0];
                                // // Se calcula el descuento de las escursiones de este socio. 10%
                                // Double descuentoExcursiones = Double.parseDouble(retornoArray[1])
                                // - (Double.parseDouble(retornoArray[1]) * 10 / 100);
                                // // Se genera el precio final de facturación
                                // facturacion = precioCuotaDescuento + descuentoExcursiones;
                                // // Se manda el resultado a la vista
                                // respuesta += "\n El socio factura " + facturacion
                                // + " euros mensuales. (10% de dto. incluido en el precio final.)";
                                // } else if (tipoSocio.equals("Infantil")) {
                                // // Aplicamos un descuento de la cuota mensual 50%
                                // Double precioCuotaDescuento = cuotaMensual
                                // - (cuotaMensual * 50 / 100);
                                // respuesta += "\n - Coste de la cuota: " + precioCuotaDescuento + " euros.";
                                // // Obtener listado de escursiones y precio:
                                // // Al intentar obtener datos de un medio exteno, en este caso la BBDD,
                                // debemos
                                // // usar try para comprobar posibles excepciones.
                                // try {
                                // retornoArray = InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio);
                                // } catch (Exception e) {
                                // RespView.excepcionesControllerView(e.getMessage());
                                // }
                                // respuesta += retornoArray[0];
                                // // Se genera el precio final de facturación
                                // facturacion = precioCuotaDescuento + Double.parseDouble(retornoArray[1]);
                                // // Se manda el resultado a la vista
                                // respuesta += "\n El socio factura " + facturacion + " euros mensuales.";
                            }
                            return null;
                        } catch (Exception e) {
                            Platform.runLater(() -> NotificacionView.Notificacion("error", "Error en el controlador",
                                    "Error en el controlador: " + e.getMessage()));
                        } finally {
                            Platform.runLater(() -> tInfo1.setText(""));
                        }
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }
        });
    }

    // Este metodo permite filtrar usuarios en la tabla usando tres CheckBox con
    // fx:id
    // filterEstandar // filterFederado // filterInfantil
    public void filtrarSocioPorTipoEnTabla() {
        filterEstandar.selectedProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        filterFederado.selectedProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        filterInfantil.selectedProperty().addListener((observable, oldValue, newValue) -> applyFilter());
    }

    // Este metodo permite filtrar usuarios en la tabla usando un TextField con
    // fx:id
    // tfNumeroSocio
    public void filtrarSocioPorNumeroEnTabla() {
        tfNumeroSocio.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(socio -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String numeroSocio = String.valueOf(((SocioModel) socio).getNumeroSocio());
                return numeroSocio.contains(newValue);
            });
        });
    }

    // Este metodo permite cargar usuarios en la tabla, el nombre de la tabla,
    // columnas y aviso de carga de datos son:
    // tabla-id:taTodosLosSocios // column-id:taNumeroSocio
    // column-id:taNombre // text-id:tInfo
    @FXML
    public void cargarLosSociosEnTabla() {
        tInfo.setText("Cargando datos ...");
        // Iniciamos la tabla
        taNumeroSocio.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof SocioEstandarModel) {
                SocioEstandarModel socio = (SocioEstandarModel) item;
                return new SimpleIntegerProperty(socio.getNumeroSocio()).asObject();
            } else if (item instanceof SocioFederadoModel) {
                SocioFederadoModel socio = (SocioFederadoModel) item;
                return new SimpleIntegerProperty(socio.getNumeroSocio()).asObject();
            } else if (item instanceof SocioInfantilModel) {
                SocioInfantilModel socio = (SocioInfantilModel) item;
                return new SimpleIntegerProperty(socio.getNumeroSocio()).asObject();
            }
            return null;
        });
        taNombre.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof SocioEstandarModel) {
                SocioEstandarModel socio = (SocioEstandarModel) item;
                return new SimpleStringProperty(socio.getNombre());
            } else if (item instanceof SocioFederadoModel) {
                SocioFederadoModel socio = (SocioFederadoModel) item;
                return new SimpleStringProperty(socio.getNombre());
            } else if (item instanceof SocioInfantilModel) {
                SocioInfantilModel socio = (SocioInfantilModel) item;
                return new SimpleStringProperty(socio.getNombre());
            }
            return null;
        });
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    // Obtenemos los objetos de los socios
                    ArrayList<SocioEstandarModel> socioEstandarModels = SocioEstandarModel.obtenerSocios();
                    ArrayList<SocioFederadoModel> socioFederadoModels = SocioFederadoModel.obtenerSocios();
                    ArrayList<SocioInfantilModel> socioInfantilModels = SocioInfantilModel.obtenerSocios();
                    // Create an ObservableList and add the items to it
                    ObservableList<Object> allSocios = FXCollections.observableArrayList();
                    allSocios.addAll(socioEstandarModels);
                    allSocios.addAll(socioFederadoModels);
                    allSocios.addAll(socioInfantilModels);
                    // Objetos filtrados si es necesario
                    filteredData = new FilteredList<>(allSocios);
                    // Añadimos los socios a la tabla
                    taTodosLosSocios.setItems(filteredData);
                } catch (Exception e) {
                    Platform.runLater(() -> NotificacionView.Notificacion("error", "Error en el controlador",
                            "Error en el controlador: " + e.getMessage()));
                } finally {
                    Platform.runLater(() -> tInfo.setText(""));
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}