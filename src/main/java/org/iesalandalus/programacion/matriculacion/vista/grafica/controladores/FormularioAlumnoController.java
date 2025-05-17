package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.time.LocalDate;

public class FormularioAlumnoController {

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField campoCorreo;

    @FXML
    private TextField campoDni;

    @FXML
    private DatePicker campoFechaNacimiento;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoTelefono;

    @FXML
    private Text labelTitulo;
    private ObservableList<Alumno> listaAlumnos;

    private Alumno registro;

    public void setListado(ObservableList<Alumno> listaAlumnos)
    {
        this.listaAlumnos = listaAlumnos;
    }


    public void setRegistro(Alumno p) {
        this.registro = p;

        if(this.registro != null) {
            //labelTitulo.setText("Editar Alumno");
            this.campoDni.setText(this.registro.getDni());
            this.campoNombre.setText(this.registro.getNombre());
            this.campoTelefono.setText(this.registro.getTelefono());
            this.campoCorreo.setText(this.registro.getCorreo());
            this.campoFechaNacimiento.setValue(this.registro.getFechaNacimiento());
        } else {
            //labelTitulo.setText("Añadir Alumno");
            // Limpiar el DatePicker al añadir nuevo alumno
            this.campoFechaNacimiento.setValue(null);
        }
    }
    public Alumno getRegistro()
    {
        return this.registro;
    }

    @FXML
    void Guardar(ActionEvent event)
    {
        try
        {
            String nombre = this.campoNombre.getText();
            String telefono = this.campoTelefono.getText();
            String correo = this.campoCorreo.getText();
            String dni = this.campoDni.getText();
            LocalDate fechaNacimiento = campoFechaNacimiento.getValue();


            if(this.registro == null) // Añadimos una nueva persona
            {
                Alumno nuevoAlumno = new Alumno(nombre, telefono, correo, dni, fechaNacimiento);
                if (!this.listaAlumnos.contains(nuevoAlumno)) {
                    //this.listaAlumnos.add(nuevoAlumno); // Añadir a la lista
                    this.registro = nuevoAlumno;
                    this.aviso("Alumno añadido correctamente", Alert.AlertType.CONFIRMATION);
                    Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    escenarioActual.close();
                }
                else
                {
                    this.aviso("El alumno ya existe.", Alert.AlertType.ERROR);
                }
            }
            else
            {
                // Crear una copia del alumno original
                Alumno alumnoOriginal = new Alumno(this.registro);
                this.registro.setNombre(nombre);
                this.registro.setDni(dni);
                this.registro.setTelefono(telefono);
                this.registro.setCorreo(correo);
                this.registro.setFechaNacimiento(fechaNacimiento);
                // Notificar a la tabla reemplazando el elemento
                int index = listaAlumnos.indexOf(alumnoOriginal);
                if (index != -1) {
                    listaAlumnos.set(index, this.registro);
                }
                this.aviso("Alumno modificado correctamente.", Alert.AlertType.CONFIRMATION);
                Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                escenarioActual.close();
            }
        }
        catch(NumberFormatException e)
        {
            this.aviso("La edad tiene que ser un número.", Alert.AlertType.ERROR);
        }
        catch(Exception e)
        {
            this.aviso(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void aviso(String aviso, Alert.AlertType tipo)
    {
        Alert a = new Alert(tipo);
        a.setTitle("Aviso...");
        a.setHeaderText(aviso);
        a.show();
    }

}

