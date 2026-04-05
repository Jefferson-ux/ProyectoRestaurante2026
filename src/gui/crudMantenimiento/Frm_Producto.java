/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.crudMantenimiento;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//Metodo de producto
import logic.dao.ProductoMethod;
//Metodo de unidad medida para el jcombobox
import logic.dao.UnidadMedidaMethod;

public class Frm_Producto extends javax.swing.JFrame {
    //Modelo para mostrar datos en ta tabla
    DefaultTableModel modeloTablaProducto = new DefaultTableModel();
    
    //Objeto conexión a la base de datos
    ProductoMethod PR;
    UnidadMedidaMethod UM;

    //VAriable para comprobar cambios en mdoificar
    private String nombreOriginal;
    private String precioOriginal;
    private String stockActualOriginal;
    private String stockMinimoOriginal;
    private String unidadOriginal = "";
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Frm_Producto.class.getName());

    public Frm_Producto() {
        initComponents();
        
        //Posicion
        this.setLocationRelativeTo(null);
       
        //Metodo para cargar el combobox
        cargarUnidadMedida();
        
        //definir los encabezados de la tabla
        String titulos[]={"Codigo Del Producto"," Nombre del producto", "Precio del producto","stock minimo","Stock actual","unidad de medida"};
        
        //Asignar los tiutlos al modelo
        modeloTablaProducto.setColumnIdentifiers(titulos);
        
        //Establecer el modelo a la JTable
        JTABLE_Mant_Producto.setModel(modeloTablaProducto);
        
        //Desabilitar campo de codigo (solo se mostrara no se escribe)
        txtcodigoproducto.setEnabled(false);
        BTN_Guardar.setEnabled(false);
        BTN_Modificar.setEnabled(false);
        BTN_Desactivar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtcodigoproducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtstockActual = new javax.swing.JTextField();
        jComboBox_unidad_medida = new javax.swing.JComboBox<>();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtstockMinimo = new javax.swing.JTextField();
        txtPreciounitario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTABLE_Mant_Producto = new javax.swing.JTable();
        BTN_EXCEL = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        TXT_BuscarMesas = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        BTN_Desactivar1 = new javax.swing.JButton();
        BTN_Cerrar1 = new javax.swing.JButton();
        BTN_PDF = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        BTN_VerProductos = new javax.swing.JButton();
        BTN_Desactivar = new javax.swing.JButton();
        BTN_Modificar = new javax.swing.JButton();
        BTN_Guardar = new javax.swing.JButton();
        BTN_Nuevo = new javax.swing.JButton();
        BTN_Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MANTENIMIENTO DE PRODUCTO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 830, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 0, 51), null));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel2.setText("Codigo del  Producto");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Stock actual");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 80, -1));

        txtcodigoproducto.setEditable(false);
        txtcodigoproducto.setBackground(new java.awt.Color(255, 255, 255));
        txtcodigoproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcodigoproducto.setForeground(new java.awt.Color(0, 0, 204));
        txtcodigoproducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcodigoproducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtcodigoproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 160, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nombre del Producto*");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 140, -1));

        txtstockActual.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtstockActual.setForeground(new java.awt.Color(0, 0, 204));
        txtstockActual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtstockActual.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtstockActual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstockActualKeyTyped(evt);
            }
        });
        jPanel1.add(txtstockActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 140, 30));

        jPanel1.add(jComboBox_unidad_medida, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 330, 30));

        txtNombreProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombreProducto.setForeground(new java.awt.Color(0, 0, 204));
        txtNombreProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombreProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyTyped(evt);
            }
        });
        jPanel1.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 330, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Precio unitario*");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 100, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("unidad de medida");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 100, -1));

        txtstockMinimo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtstockMinimo.setForeground(new java.awt.Color(0, 0, 204));
        txtstockMinimo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtstockMinimo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtstockMinimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstockMinimoKeyTyped(evt);
            }
        });
        jPanel1.add(txtstockMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 140, 30));

        txtPreciounitario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPreciounitario.setForeground(new java.awt.Color(0, 0, 204));
        txtPreciounitario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPreciounitario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPreciounitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPreciounitarioKeyTyped(evt);
            }
        });
        jPanel1.add(txtPreciounitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 140, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Stock mínimo*");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 90, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 730, 300));

        JTABLE_Mant_Producto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        JTABLE_Mant_Producto.setForeground(new java.awt.Color(0, 0, 204));
        JTABLE_Mant_Producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        JTABLE_Mant_Producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTABLE_Mant_ProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTABLE_Mant_Producto);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 920, 220));

        BTN_EXCEL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_EXCEL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/excel.png"))); // NOI18N
        BTN_EXCEL.setText("     Exportar XLSX");
        BTN_EXCEL.addActionListener(this::BTN_EXCELActionPerformed);
        getContentPane().add(BTN_EXCEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 650, 170, 50));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ingresar el Nombre de la Facultad");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, 30));

        TXT_BuscarMesas.addActionListener(this::TXT_BuscarMesasActionPerformed);
        TXT_BuscarMesas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TXT_BuscarMesasKeyReleased(evt);
            }
        });
        jPanel2.add(TXT_BuscarMesas, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 13, 290, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_search_white.png"))); // NOI18N
        jLabel5.setText("BUSCAR");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 120, 30));

        BTN_Desactivar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Desactivar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_delete.png"))); // NOI18N
        BTN_Desactivar1.setText("     QUITAR");
        BTN_Desactivar1.addActionListener(this::BTN_Desactivar1ActionPerformed);
        jPanel2.add(BTN_Desactivar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(732, 280, 165, 48));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 920, 50));

        BTN_Cerrar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Cerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_close.png"))); // NOI18N
        BTN_Cerrar1.setText("     Cerrar");
        BTN_Cerrar1.addActionListener(this::BTN_Cerrar1ActionPerformed);
        getContentPane().add(BTN_Cerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(732, 650, 165, 50));

        BTN_PDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/pdf.png"))); // NOI18N
        BTN_PDF.setText("     Exportar PDF");
        BTN_PDF.addActionListener(this::BTN_PDFActionPerformed);
        getContentPane().add(BTN_PDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 650, 170, 50));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BTN_VerProductos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_VerProductos.setText("VER PLATOS DEL MENÚ");
        BTN_VerProductos.addActionListener(this::BTN_VerProductosActionPerformed);
        jPanel3.add(BTN_VerProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 165, 50));

        BTN_Desactivar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Desactivar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_delete.png"))); // NOI18N
        BTN_Desactivar.setText("     QUITAR");
        BTN_Desactivar.addActionListener(this::BTN_DesactivarActionPerformed);
        jPanel3.add(BTN_Desactivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 280, 165, 48));

        BTN_Modificar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_update.png"))); // NOI18N
        BTN_Modificar.setText("    MODIFICAR");
        BTN_Modificar.addActionListener(this::BTN_ModificarActionPerformed);
        jPanel3.add(BTN_Modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, 165, 48));

        BTN_Guardar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_save.png"))); // NOI18N
        BTN_Guardar.setText("     GUARDAR");
        BTN_Guardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BTN_GuardarMouseClicked(evt);
            }
        });
        BTN_Guardar.addActionListener(this::BTN_GuardarActionPerformed);
        jPanel3.add(BTN_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, 165, 48));

        BTN_Nuevo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_add.png"))); // NOI18N
        BTN_Nuevo.setText("      NUEVO");
        BTN_Nuevo.addActionListener(this::BTN_NuevoActionPerformed);
        jPanel3.add(BTN_Nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 165, 48));

        BTN_Cancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTN_Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_delete.png"))); // NOI18N
        BTN_Cancel.setText("     CANCELAR");
        BTN_Cancel.addActionListener(this::BTN_CancelActionPerformed);
        jPanel3.add(BTN_Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 165, 48));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtstockActualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstockActualKeyTyped
 
    }//GEN-LAST:event_txtstockActualKeyTyped

    private void txtNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyTyped
 
    }//GEN-LAST:event_txtNombreProductoKeyTyped

    private void JTABLE_Mant_ProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTABLE_Mant_ProductoMouseClicked
        int filaSeleccionada = JTABLE_Mant_Producto.getSelectedRow();
        BTN_Modificar.setEnabled(true);
        BTN_Desactivar.setEnabled(true);
        BTN_Guardar.setEnabled(false);
        BTN_Cancel.setEnabled(false);
        BTN_VerProductos.setEnabled(false);

        if (filaSeleccionada != -1) {
            // Obtener los datos de la fila seleccionada
            String codigo = JTABLE_Mant_Producto.getValueAt(filaSeleccionada, 0).toString();
            String nombreProducto = JTABLE_Mant_Producto.getValueAt(filaSeleccionada, 1).toString().trim();
            String precioUnitario = JTABLE_Mant_Producto.getValueAt(filaSeleccionada, 2).toString().trim();
            String stockActual = JTABLE_Mant_Producto.getValueAt(filaSeleccionada, 3).toString().trim();
            String stockMinimo = JTABLE_Mant_Producto.getValueAt(filaSeleccionada, 4).toString().trim();
            String nombreUnidadMedida = JTABLE_Mant_Producto.getValueAt(filaSeleccionada, 5).toString().trim();

            // Mostrar en los controles
            txtcodigoproducto.setText(codigo);
            txtNombreProducto.setText(nombreProducto);
            txtPreciounitario.setText(precioUnitario);
            txtstockActual.setText(stockActual);
            txtstockMinimo.setText(stockMinimo);

            // Guardar valores originales para comparación
            nombreOriginal = nombreProducto;
            precioOriginal = precioUnitario;
            stockActualOriginal = stockActual;
            stockMinimoOriginal = stockMinimo;
            unidadOriginal = nombreUnidadMedida;

            // Buscar coincidencia en el ComboBox ignorando mayúsculas
            boolean encontrado = false;
            for (int i = 0; i < jComboBox_unidad_medida.getItemCount(); i++) {
                String item = jComboBox_unidad_medida.getItemAt(i).trim();
                if (item.equalsIgnoreCase(nombreUnidadMedida)) {
                    jComboBox_unidad_medida.setSelectedIndex(i);
                    encontrado = true;
                    break;
                }
            }

            // Si no se encontró, mostrar alerta opcional
            if (!encontrado) {
                JOptionPane.showMessageDialog(this,
                    "No se encontró la facultad en la lista del combo.",
                    "Facultad no encontrada", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_JTABLE_Mant_ProductoMouseClicked

    private void BTN_EXCELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_EXCELActionPerformed
       
    }//GEN-LAST:event_BTN_EXCELActionPerformed

    private void TXT_BuscarMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_BuscarMesasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXT_BuscarMesasActionPerformed

    private void TXT_BuscarMesasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXT_BuscarMesasKeyReleased

    }//GEN-LAST:event_TXT_BuscarMesasKeyReleased

    private void BTN_Desactivar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Desactivar1ActionPerformed
       
    }//GEN-LAST:event_BTN_Desactivar1ActionPerformed

    private void BTN_Cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Cerrar1ActionPerformed

    }//GEN-LAST:event_BTN_Cerrar1ActionPerformed

    private void BTN_PDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_PDFActionPerformed
        
    }//GEN-LAST:event_BTN_PDFActionPerformed

    private void BTN_VerProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_VerProductosActionPerformed
        this.listarProductos();
        this.BTN_Guardar.setEnabled(false);
        this.BTN_Desactivar.setEnabled(false);
        this.BTN_Modificar.setEnabled(false);
    }//GEN-LAST:event_BTN_VerProductosActionPerformed

    private void BTN_DesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_DesactivarActionPerformed
       
    }//GEN-LAST:event_BTN_DesactivarActionPerformed

    private void BTN_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_ModificarActionPerformed
        
    }//GEN-LAST:event_BTN_ModificarActionPerformed

    private void BTN_GuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTN_GuardarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BTN_GuardarMouseClicked

    private void BTN_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_GuardarActionPerformed

    }//GEN-LAST:event_BTN_GuardarActionPerformed

    private void BTN_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_NuevoActionPerformed
        try {
            // Obtener datos del formulario
            String codigoStr = txtcodigoproducto.getText().trim();
            String nuevoNombreProducto = txtNombreProducto.getText().trim();
            String nuevoPrecioUnitario = txtPreciounitario.getText().trim();
            String nuevoStockActual = txtstockActual.getText().trim();
            String nuevoStockMinimo = txtstockMinimo.getText().trim();
            String nombreUnidadMedida = (String) jComboBox_unidad_medida.getSelectedItem();
    

            // Validar campos obligatorios
            if (codigoStr.isEmpty() || nuevoNombreProducto.isEmpty() || nuevoPrecioUnitario.isEmpty() || nuevoStockActual.isEmpty() || nuevoStockMinimo.isEmpty() || nombreUnidadMedida == null || nombreUnidadMedida.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar longitud del nombre
            if (nuevoNombreProducto.length() > 85) {
                JOptionPane.showMessageDialog(this, "El nombre de la escuela no debe exceder 85 caracteres.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar si hubo cambios
            if (nuevoNombreProducto.equalsIgnoreCase(nombreOriginal) && nombreUnidadMedida.equalsIgnoreCase(unidadOriginalOriginal)) {
                JOptionPane.showMessageDialog(this, "No se detectaron cambios en los datos.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Convertir código a entero
            int codigoEscuela = Integer.parseInt(codigoStr);

            // Obtener código de la facultad
            int codigoFacultad = conexionBD.obtenerCodigoFacultad(nombreFacultad);
            if (codigoFacultad == -1) {
                JOptionPane.showMessageDialog(this, "La facultad seleccionada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar duplicidad de nombre con otras escuelas
            if (conexionBD.existeEscuelaConNombre(nuevoNombreEscuela, codigoEscuela)) {
                JOptionPane.showMessageDialog(this, "Ya existe otra escuela con el mismo nombre.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

             // Llamar al método de modificación
            conexionBD.ModificarEscuelaProfecional(codigoEscuela, nuevoNombreEscuela, codigoFacultad);

            JOptionPane.showMessageDialog(this, "Escuela profesional actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Refrescar tabla y limpiar
            listarEscuelasProfesionales();
            limpiarCamposEscuela();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código de escuela no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BTN_NuevoActionPerformed

    private void BTN_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_CancelActionPerformed
        JTABLE_Mant_Producto.setRowSelectionAllowed(true);
        JTABLE_Mant_Producto.setColumnSelectionAllowed(true);
        JTABLE_Mant_Producto.setCellSelectionEnabled(true);

        BTN_Guardar.setEnabled(true);
        BTN_Desactivar.setEnabled(false);
        BTN_Modificar.setEnabled(false);
        BTN_Nuevo.setVisible(true);
        BTN_Cancel.setVisible(false);
        JTABLE_Mant_Producto.setEnabled(true);
    }//GEN-LAST:event_BTN_CancelActionPerformed

    private void txtstockMinimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstockMinimoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstockMinimoKeyTyped

    private void txtPreciounitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPreciounitarioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPreciounitarioKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Frm_Producto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_Cancel;
    private javax.swing.JButton BTN_Cerrar1;
    private javax.swing.JButton BTN_Desactivar;
    private javax.swing.JButton BTN_Desactivar1;
    private javax.swing.JButton BTN_EXCEL;
    private javax.swing.JButton BTN_Guardar;
    private javax.swing.JButton BTN_Modificar;
    private javax.swing.JButton BTN_Nuevo;
    private javax.swing.JButton BTN_PDF;
    private javax.swing.JButton BTN_VerProductos;
    private javax.swing.JTable JTABLE_Mant_Producto;
    private javax.swing.JTextField TXT_BuscarMesas;
    private javax.swing.JComboBox<String> jComboBox_unidad_medida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPreciounitario;
    private javax.swing.JTextField txtcodigoproducto;
    private javax.swing.JTextField txtstockActual;
    private javax.swing.JTextField txtstockMinimo;
    // End of variables declaration//GEN-END:variables
    
    //llenar elementos en el jcombobox el metodo viene de la clase
    //y la consulta de la vista
    private void cargarUnidadMedida() {
        try {
            jComboBox_unidad_medida.removeAllItems();
            jComboBox_unidad_medida.addItem("<<Seleccionar>>"); // Opción por defecto

            ResultSet rs = UM.listarUnidadMedida();
            while (rs.next()) {
                jComboBox_unidad_medida.addItem(rs.getString("nombre_unidad_medida"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las unidades de medidas: " + e.getMessage());
        }
    }
    
    //método para mostrar registros en el jtable
    private void listarProductos() {
        try {

            //Ordenar Asc, Desc
            JTABLE_Mant_Producto.setAutoCreateRowSorter(true);

            // Limpiar la tabla antes de cargar nuevos datos
            modeloTablaProducto.setRowCount(0);

            ResultSet rs = PR.listarProductos();
            while (rs.next()) {
                Object fila[] = {
                    rs.getInt("ID"),                              // p.id_producto
                    rs.getString("Nombre del Producto"),         // p.nombre_producto
                    rs.getString("Unidad de Medida"),            // um.nombre_unidad_medida
                    rs.getString("Abreviatura"),                 // um.abreviatura
                    rs.getString("Precio"),                      // CONCAT('S/ ', ...)
                    rs.getInt("Stock Mínimo"),                   // p.stock_minimo
                    rs.getInt("Stock Actual")                    // p.stock_actual
                };
                modeloTablaProducto.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar Unidades de medida: " + e.getMessage());
        }
        /*finaliza:*/   
    }

}
