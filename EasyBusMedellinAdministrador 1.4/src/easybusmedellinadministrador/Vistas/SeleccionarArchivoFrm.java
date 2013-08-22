/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SeleccionarArchivo.java
 *
 * Created on 29-nov-2011, 0:12:07
 */
package easybusmedellinadministrador.Vistas;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author simon
 */
public class SeleccionarArchivoFrm extends javax.swing.JFrame {

    /** Creates new form SeleccionarArchivo */
    private AgregarBusFrm interfaz;
    private int x;
    
    public SeleccionarArchivoFrm(AgregarBusFrm interfaz,int x) {
        super("Seleccione el archivo a encriptar");
        this.interfaz = interfaz;
        this.x = x;
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        jFileChooser1.setMultiSelectionEnabled(false);
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("Archivo de texto", "txt"));
        jFileChooser1.setAcceptAllFileFilterUsed(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jFileChooser1.setCurrentDirectory(new java.io.File("C:\\Users\\Edgardo\\Dropbox\\Proyectos\\EasyBus\\Coordenanas Buses\\TXT"));
        jFileChooser1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jFileChooser1.setOpaque(true);
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
    if(evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
        if(x == 1){
            interfaz.getTxtDireccion().setText(jFileChooser1.getSelectedFile().getPath());
        }else{
            interfaz.getTxtDireccion2().setText(jFileChooser1.getSelectedFile().getPath());
        }
            this.dispose();
        }else if(evt.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)){
            if(x == 1){
                interfaz.getTxtDireccion().setText("");
            }else{
                interfaz.getTxtDireccion2().setText("");
            }
            
            this.dispose();
        }
}//GEN-LAST:event_jFileChooser1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}
