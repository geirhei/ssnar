/*
 * This code is written as a part of a Master Thesis
 * the spring of 2016.
 *
 * Thor Eivind Andersen and Mats Rødseth (Master 2016 @ NTNU)
 */
package no.ntnu.tem.gui;

import no.ntnu.tem.robot.Robot;

/**
 * GUI that gives the user the ability to control a robot manually
 *
 * @author Thor Eivind and Mats (Master 2016 @ NTNU)
 */
public class ManualDriveCoordinatesGUI extends javax.swing.JFrame {

    private final MainGUI mainGUI;
    private final Robot robot;

    /**
     * Constructor for the class ManualDriveGUI
     *
     * @param robot the robot to control
     * @param mainGUI the systems mainGUI.
     */
    ManualDriveCoordinatesGUI(Robot robot, MainGUI mainGUI) {
        initComponents();
        this.mainGUI = mainGUI;
        this.robot = robot;
        this.setLocationRelativeTo(mainGUI);
        lblRobotName.setText(robot.getName());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        ftxtfAngle = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        ftxtfDistance = new javax.swing.JFormattedTextField();
        btnSendCommand = new javax.swing.JButton();
        lblRobotName = new javax.swing.JLabel();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel3.setText("Angle:");

        ftxtfAngle.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtfAngle.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        ftxtfAngle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ftxtfAngleKeyPressed(evt);
            }
        });

        jLabel4.setText("Distance");

        ftxtfDistance.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtfDistance.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        ftxtfDistance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ftxtfDistanceKeyPressed(evt);
            }
        });

        btnSendCommand.setText("Send Command");
        btnSendCommand.setFocusable(false);
        btnSendCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendCommandActionPerformed(evt);
            }
        });

        lblRobotName.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        lblRobotName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRobotName.setText("Robotname");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ftxtfDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnSendCommand))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxtfAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
            .addComponent(lblRobotName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRobotName)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ftxtfAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ftxtfDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSendCommand)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendCommandActionPerformed
        ftxtfDistance.requestFocus();
        ftxtfAngle.requestFocus();
        try {
            int angle = Integer.parseInt(ftxtfAngle.getText());
            int distance = Integer.parseInt(ftxtfDistance.getText());
            System.out.println("angle " + angle);
            System.out.println("Distance " + distance);
            mainGUI.getApplication().writeCommandToRobot(robot.getId(), robot.getName(), angle, distance);
            ftxtfDistance.setText("0");
            ftxtfAngle.setText("0");
        } catch (NumberFormatException nfe) {

        }
    }//GEN-LAST:event_btnSendCommandActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void ftxtfAngleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtfAngleKeyPressed
        isEnter(evt.getKeyCode());
    }//GEN-LAST:event_ftxtfAngleKeyPressed

    private void ftxtfDistanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxtfDistanceKeyPressed
        isEnter(evt.getKeyCode());
    }//GEN-LAST:event_ftxtfDistanceKeyPressed

    /**
     * Checks whether the KeyCode is the EnterCode or not, if it is Enter, the
     * method calls btnSendCommandActionPerformed();
     *
     * @param code KeyCode to check
     */
    private void isEnter(int code) {
        if (code == 10) {
            btnSendCommandActionPerformed(null);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendCommand;
    private javax.swing.JFormattedTextField ftxtfAngle;
    private javax.swing.JFormattedTextField ftxtfDistance;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblRobotName;
    // End of variables declaration//GEN-END:variables
}
