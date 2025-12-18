package Logica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Billetera Virtual");
        setSize(400, 500); // Tamaño vertical
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());

        // 1. Título bonito arriba
        JLabel titulo = new JLabel("Bienvenido a tu Billetera", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Margen
        add(titulo, BorderLayout.NORTH);

        // 2. Panel central para los botones (GridLayout para que sean verticales)
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10)); // 5 filas, 1 columna, espacio de 10px
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Márgenes laterales

        // Agregar botones
        panelBotones.add(crearBoton("Registro de Usuario", e -> registrarUsuario()));
        panelBotones.add(crearBoton("Consultar Saldo", e -> consultarSaldo()));
        panelBotones.add(crearBoton("Realizar Transacción", e -> realizarTransaccion()));
        panelBotones.add(crearBoton("Administrador", e -> abrirAdmin()));
        panelBotones.add(crearBoton("Salir", e -> System.exit(0)));

        add(panelBotones, BorderLayout.CENTER);

        // Hacer visible
        setVisible(true);
    }

    private JButton crearBoton(String texto, ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setFocusPainted(false);
        btn.addActionListener(accion);
        return btn;
    }

    // Métodos puente a tu lógica existente (tendrás que adaptar tu Main)
    private void registrarUsuario() {
        // Aquí llamas a tus métodos existentes, quizás usando JDialogs para los inputs
        UI.mostrarMensaje("Ir a registro...");
    }
    private void consultarSaldo() { /* ... */ }
    private void realizarTransaccion() { /* ... */ }
    private void abrirAdmin() { /* ... */ }
}