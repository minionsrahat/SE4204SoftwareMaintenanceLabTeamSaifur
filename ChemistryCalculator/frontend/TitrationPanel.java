package ChemistryCalculator.frontend;

import ChemistryCalculator.backend.Converter;
import ChemistryCalculator.backend.InsufficientDataException;
import ChemistryCalculator.backend.Titration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;


public class TitrationPanel extends JPanel {

    private static final Font SEGOE_UI = new Font("Segoe UI", 1,  14);
    private static final Color MAIN_COLOR = new Color(64, 43,  100);
    private static final Color GRAY = new Color(204, 204,  204);

    private final JLabel labelForAcidMoleTextfield = new JLabel();
    private final JTextField acidMoleTextfield = new JTextField();

    private final JLabel labelForAcidVolumeTextfield = new JLabel();
    private final JTextField acidVolumeTextfield = new JTextField();

    private final JLabel labelForAcidMolarityTextfield = new JLabel();
    private final JTextField acidMolarityTextfield = new JTextField();

    private final JLabel labelForBaseMoleTextfield = new JLabel();
    private final JTextField baseMoleTextfield = new JTextField();

    private final JLabel labelForBaseVolumeTextfield = new JLabel();
    private final JTextField baseVolumeTextfield = new JTextField();

    private final JLabel labelForBaseMolarityTextfield = new JLabel();
    private final JTextField baseMolarityTextfield = new JTextField();

    private final JButton getUnknownValueButton = new JButton();
    private final JButton clearButton = new JButton();

    private final JComboBox<String> acidMolarityUnitComboBox = new JComboBox<>();
    private final JComboBox<String> acidVolumeUnitComboBox = new JComboBox<>();
    private final JComboBox<String> baseMolarityUnitComboBox = new JComboBox<>();
    private final JComboBox<String> baseVolumeUnitComboBox = new JComboBox<>();

    private final JLabel notificationLabel = new JLabel();
    private final JPanel notificationPanel = new JPanel();

    public TitrationPanel() {
        initComponent();
        setComponentLayout();
    }


    private void setFontPropertyToTextFieldLabel(JLabel label, String text){
        label.setFont(SEGOE_UI);
        label.setForeground(MAIN_COLOR);
        label.setText(text);
    }


    private void setButtonProperty(JButton btn,String text){
        btn.setBackground(MAIN_COLOR);
        btn.setFont(SEGOE_UI);
        btn.setForeground(GRAY);
        btn.setText(text);
        btn.setAutoscrolls(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));


    }

    public void setComboxBoxProperty( JComboBox<String> comboBox,String unit){
        comboBox.setFont(SEGOE_UI);
        comboBox.setForeground(MAIN_COLOR);
        if(unit.equalsIgnoreCase("molarity")){
            comboBox.setModel(new DefaultComboBoxModel<>(
                    new String[]{
                            "molars",
                            "decimolars",
                            "centimolars",
                            "millimolars",
                            "micromolars",
                            "nanomolars"
                    }));
            comboBox.setSelectedIndex(0);
        }
        else if(unit.equalsIgnoreCase("base")){
            comboBox.setModel(new DefaultComboBoxModel<>(
                    new String[]{
                            "deciliters",
                            "milliliters",
                            "centiliters",
                            "liters",
                            "cubic_decimeters",
                            "cubic_millimeters",
                            "cubic_centimeters",
                    }
            ));
            comboBox.setSelectedIndex(1);
        }

    }


    private void initComponent() {

        setFontPropertyToTextFieldLabel(labelForAcidMolarityTextfield,"Molarity of Acid :");
        setFontPropertyToTextFieldLabel(labelForAcidVolumeTextfield,"Volume of Acid :");
        setFontPropertyToTextFieldLabel(labelForBaseVolumeTextfield,"Volume of Base :");

        setButtonProperty(clearButton,"Clear");
        clearButton.addActionListener(this::clearButtonActionPerformed);

        setButtonProperty(getUnknownValueButton,"Get unknown value");
        getUnknownValueButton.addActionListener(this::getUnknownValueButtonActionPerformed);

        setComboxBoxProperty(acidMolarityUnitComboBox,"molarity");
        acidMolarityUnitComboBox.addActionListener(this::acidMolarityUnitComboBoxActionPerformed);
        acidMolarityUnitComboBox.addItemListener(this::acidMolarityUnitComboBoxItemStateChanged);

        setComboxBoxProperty(baseMolarityUnitComboBox,"molarity");
        baseMolarityUnitComboBox.addActionListener(this::baseMolarityUnitComboBoxActionPerformed);
        baseMolarityUnitComboBox.addItemListener(this::baseMolarityUnitComboBoxItemStateChanged);

        setComboxBoxProperty(baseVolumeUnitComboBox,"base");
        baseVolumeUnitComboBox.addActionListener(this::baseVolumeUnitComboBoxActionPerformed);
        baseVolumeUnitComboBox.addItemListener(this::baseVolumeUnitComboBoxItemStateChanged);

        setComboxBoxProperty(acidVolumeUnitComboBox,"base");
        acidVolumeUnitComboBox.addActionListener(this::acidVolumeUnitComboBoxActionPerformed);
        acidVolumeUnitComboBox.addItemListener(this::acidVolumeUnitComboBoxItemStateChanged);

        labelForAcidMoleTextfield.setFont(SEGOE_UI);
        labelForAcidMoleTextfield.setForeground(MAIN_COLOR);
        labelForAcidMoleTextfield.setText("Number of moles of acid :");

        labelForBaseMoleTextfield.setFont(SEGOE_UI);
        labelForBaseMoleTextfield.setForeground(MAIN_COLOR);
        labelForBaseMoleTextfield.setText("Number of moles of base : ");


        notificationPanel.setBackground(new Color(51, 153, 0));
        notificationPanel.setVisible(true);
        notificationLabel.setBackground(Color.red);
        notificationLabel.setFont(SEGOE_UI);
        notificationLabel.setForeground(Color.white);
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationLabel.setText("Fill up any 5 fields to get unknown value");

        labelForBaseMolarityTextfield.setFont(SEGOE_UI);
        labelForBaseMolarityTextfield.setForeground(MAIN_COLOR);
        labelForBaseMolarityTextfield.setText("Molarity of Base :");
    }

    private void setComponentLayout() {

        //setting notification Panel Layout
        GroupLayout notificationPanelLayout = new GroupLayout(notificationPanel);
        notificationPanel.setLayout(notificationPanelLayout);
        notificationPanelLayout.setHorizontalGroup(
                notificationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, notificationPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(notificationLabel, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE))
        );
        notificationPanelLayout.setVerticalGroup(
                notificationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(notificationPanelLayout.createSequentialGroup()
                                .addComponent(notificationLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );


        //main Layout
        GroupLayout Layout = new GroupLayout(this);
        this.setLayout(Layout);
        Layout.setHorizontalGroup(
                Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(Layout.createSequentialGroup()
                                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(labelForAcidMolarityTextfield, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelForAcidMoleTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelForBaseMoleTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelForBaseMolarityTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelForAcidVolumeTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelForBaseVolumeTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(acidMoleTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                                        .addComponent(baseVolumeTextfield, GroupLayout.Alignment.LEADING)
                                                        .addComponent(baseMolarityTextfield, GroupLayout.Alignment.LEADING)
                                                        .addComponent(acidVolumeTextfield, GroupLayout.Alignment.LEADING)
                                                        .addComponent(acidMolarityTextfield, GroupLayout.Alignment.LEADING)
                                                        .addComponent(baseMoleTextfield))
                                                .addGap(18, 18, 18)
                                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(acidMolarityUnitComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(baseMolarityUnitComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(baseVolumeUnitComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(acidVolumeUnitComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(Layout.createSequentialGroup()
                                                .addComponent(getUnknownValueButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(clearButton)))
                                .addContainerGap(122, Short.MAX_VALUE))
        );
        Layout.setVerticalGroup(
                Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(labelForAcidMolarityTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(acidMolarityTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                        .addComponent(acidMolarityUnitComboBox))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelForAcidVolumeTextfield, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(acidVolumeUnitComboBox)
                                        .addComponent(acidVolumeTextfield, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelForBaseMolarityTextfield, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(baseMolarityTextfield, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                        .addComponent(baseMolarityUnitComboBox))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(labelForBaseVolumeTextfield, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(baseVolumeTextfield, GroupLayout.Alignment.LEADING)
                                        .addComponent(baseVolumeUnitComboBox, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelForAcidMoleTextfield, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                        .addComponent(acidMoleTextfield, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelForBaseMoleTextfield, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(baseMoleTextfield, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(getUnknownValueButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
    }


    public String getMolarityOfAcid() {
        String acidMolarity = acidMolarityTextfield.getText();
        if(acidMolarity.isEmpty()) {
            return "";
        } else {
            return convertToBaseUnit(acidMolarity,acidMolarityUnitComboBox,"molars");
        }
    }

    public String getMolarityOfBase() {
        String baseMolarity = baseMolarityTextfield.getText();
        if(baseMolarity.isEmpty()) {
            return "";
        } else {
            return convertToBaseUnit(baseMolarity,baseMolarityUnitComboBox,"molars");
        }
    }

    public String getVolumeOfAcid() {
        String volumeAcid = acidVolumeTextfield.getText();
        if(volumeAcid.isEmpty()) {
            return "";
        } else {
            return convertToBaseUnit(volumeAcid,acidVolumeUnitComboBox,"milliliters");
        }
    }

    public String getVolumeOfBase() {
        String volumeBase= baseVolumeTextfield.getText();
        if(volumeBase.isEmpty()) {
            return "";
        } else {
            return convertToBaseUnit(volumeBase,baseVolumeUnitComboBox,"milliliters");
        }
    }


    public String convertToBaseUnit(String value,JComboBox<String> comboBox,String base){
        String unit = comboBox.getSelectedItem().toString();
        double convertedBaseValue = Converter.convert(unit, base, Double.parseDouble(value));
        return String.valueOf(convertedBaseValue);
    }

    private void getUnknownValueButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
      String molarityOfAcid = null, molarityOfBase = null, volumeOfAcid = null, volumeOfBase = null;
        try {
             molarityOfAcid=getMolarityOfAcid();
             molarityOfBase = getMolarityOfBase();
             volumeOfAcid = getVolumeOfAcid();
             volumeOfBase = getVolumeOfBase();
        }catch (NumberFormatException e) {
            notificationLabel.setText("Only numbers are allowed.");
            notificationPanel.setBackground(Color.red);
            notificationPanel.setVisible(true);
        }

        String numOfMolesOfAcid = acidMoleTextfield.getText();
        String numOfMolesOfBase = baseMoleTextfield.getText();

        Titration titration = new Titration(molarityOfAcid, molarityOfBase, volumeOfAcid, volumeOfBase, numOfMolesOfAcid, numOfMolesOfBase);

        if (acidMolarityTextfield.getText().isEmpty()) {
            try {
                acidMolarityTextfield.setText(
                        String.format("%.5f",
                                Converter.convert("molars",
                                        acidMolarityUnitComboBox.getSelectedItem().toString(),
                                        titration.getUnknownValue())
                        ));
                notificationPanel.setVisible(false);
            } catch (InsufficientDataException e) {
                notificationPanel.setBackground(Color.red);
                notificationLabel.setText(e.getMessage());
                notificationPanel.setVisible(true);
            } catch (NumberFormatException e) {
                notificationLabel.setText("Only numbers are allowed.");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
            }
            return;
        }
        if (baseMolarityTextfield.getText().isEmpty()) {
            try {
                baseMolarityTextfield.setText(
                        String.format("%.5f",
                                Converter.convert("molars",
                                        baseMolarityUnitComboBox.getSelectedItem().toString(),
                                        titration.getUnknownValue())
                        ));
                notificationPanel.setVisible(false);
            } catch (Exception e) {
                if(e instanceof InsufficientDataException){
                    notificationLabel.setText(e.getMessage());
                }
                else if (e instanceof NumberFormatException) {
                    notificationLabel.setText("Only numbers are allowed.");
                }
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
            }
            return;
        }
        if (acidVolumeTextfield.getText().isEmpty()) {
            try {

                acidVolumeTextfield.setText(
                        String.format("%.5f",
                                Converter.convert("milliliters",
                                        acidVolumeUnitComboBox.getSelectedItem().toString(),
                                        titration.getUnknownValue())
                        ));
                notificationPanel.setVisible(false);
            } catch (InsufficientDataException e) {
                notificationPanel.setBackground(Color.red);
                notificationLabel.setText(e.getMessage());
                notificationPanel.setVisible(true);
            } catch (NumberFormatException e) {
                notificationLabel.setText("Only numbers are allowed.");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
            }
            return;
        }
        if (baseVolumeTextfield.getText().isEmpty()) {
            try {
                baseVolumeTextfield.setText(
                        String.format("%.5f",
                                Converter.convert("milliliters",
                                        baseVolumeUnitComboBox.getSelectedItem().toString(),
                                        titration.getUnknownValue())
                        ));
                notificationPanel.setVisible(false);
            } catch (InsufficientDataException e) {
                notificationPanel.setBackground(Color.red);
                notificationLabel.setText(e.getMessage());
                notificationPanel.setVisible(true);
            } catch (NumberFormatException e) {
                notificationLabel.setText("Only numbers are allowed.");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
            }
            return;
        }
        if (acidMoleTextfield.getText().isEmpty()) {
            try {
                acidMoleTextfield.setText(String.format("%.5f", titration.getUnknownValue()));
                notificationPanel.setVisible(false);
            } catch (InsufficientDataException e) {
                notificationPanel.setBackground(Color.red);
                notificationLabel.setText(e.getMessage());
                notificationPanel.setVisible(true);
            } catch (NumberFormatException e) {
                notificationLabel.setText("Only numbers are allowed.");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
            }
            return;
        }
        if (baseMoleTextfield.getText().isEmpty()) {
            try {
                baseMoleTextfield.setText(String.format("%.5f", titration.getUnknownValue()));
                notificationPanel.setVisible(false);
            } catch (InsufficientDataException e) {
                notificationPanel.setBackground(Color.red);
                notificationLabel.setText(e.getMessage());
                notificationPanel.setVisible(true);
            } catch (NumberFormatException e) {
                notificationLabel.setText("Only numbers are allowed.");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
            }
        }

    }

    private void clearButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        acidMolarityTextfield.setText(null);
        baseMolarityTextfield.setText(null);
        acidVolumeTextfield.setText(null);
        baseVolumeTextfield.setText(null);
        acidMoleTextfield.setText(null);
        baseMoleTextfield.setText(null);

        notificationPanel.setVisible(false);
    }

    String acidMolarityPreviousItem = null;
    String acidMolarityNewItem = null;
    private void acidMolarityUnitComboBoxItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
        String molarityOfAcid = acidMolarityTextfield.getText();
        if (!molarityOfAcid.isEmpty()){
            if(evt.getStateChange() == ItemEvent.DESELECTED)
            {
                acidMolarityPreviousItem =  evt.getItem().toString();
            }
            else if(evt.getStateChange() == ItemEvent.SELECTED)
            {
                acidMolarityNewItem =  evt.getItem().toString();
            }
        }
    }

    private void acidMolarityUnitComboBoxActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        String molarityOfAcid = acidMolarityTextfield.getText();
        String convertedValue;
        if (!molarityOfAcid.isEmpty() && acidMolarityPreviousItem != null && acidMolarityNewItem !=null) {

            try {
                convertedValue = String.valueOf(Converter.convert(acidMolarityPreviousItem, acidMolarityNewItem, Double.parseDouble(molarityOfAcid)));
            } catch (NumberFormatException e) {
                notificationLabel.setText("Molarity of acid must be number");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
                return;
            }
            acidMolarityTextfield.setText(convertedValue);

        }
    }

    String acidVolumePreviousItem = null;
    String acidVolumeNewItem = null;
    private void acidVolumeUnitComboBoxItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
        String volumeOfAcid = acidVolumeTextfield.getText();
        if (!volumeOfAcid.isEmpty()){
            if(evt.getStateChange() == ItemEvent.DESELECTED)
            {
                acidVolumePreviousItem =  evt.getItem().toString();
            }
            else if(evt.getStateChange() == ItemEvent.SELECTED)
            {
                acidVolumeNewItem =  evt.getItem().toString();
            }
        }
    }

    private void acidVolumeUnitComboBoxActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        String volumeOfAcid = acidVolumeTextfield.getText();
        String convertedValue;
        if (!volumeOfAcid.isEmpty() && acidVolumePreviousItem != null && acidVolumeNewItem !=null) {

            try {
                convertedValue = String.valueOf(Converter.convert(acidVolumePreviousItem, acidVolumeNewItem, Double.parseDouble(volumeOfAcid)));
            } catch (NumberFormatException e) {
                notificationLabel.setText("Volume of acid must be number");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
                return;
            }
            acidVolumeTextfield.setText(convertedValue);

        }
    }

    String baseMolarityPreviousItem = null;
    String baseMolarityNewItem = null;
    private void baseMolarityUnitComboBoxItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
        String molarityOfBase = baseMolarityTextfield.getText();
        if (!molarityOfBase.isEmpty()){
            if(evt.getStateChange() == ItemEvent.DESELECTED)
            {
                baseMolarityPreviousItem =  evt.getItem().toString();
            }
            else if(evt.getStateChange() == ItemEvent.SELECTED)
            {
                baseMolarityNewItem =  evt.getItem().toString();
            }
        }
    }

    private void baseMolarityUnitComboBoxActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        String molarityOfBase = baseMolarityTextfield.getText();
        String convertedValue;
        if (!molarityOfBase.isEmpty() && baseMolarityPreviousItem != null && baseMolarityNewItem !=null) {

            try {
                convertedValue = String.valueOf(Converter.convert(baseMolarityPreviousItem, baseMolarityNewItem, Double.parseDouble(molarityOfBase)));
            } catch (NumberFormatException e) {
                notificationLabel.setText("Molarity of base must be number");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
                return;
            }
            baseMolarityTextfield.setText(convertedValue);

        }
    }

    String baseVolumePreviousItem = null;
    String baseVolumeNewItem = null;

    private void baseVolumeUnitComboBoxItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
        String volumeOfBase = baseVolumeTextfield.getText();
        if (!volumeOfBase.isEmpty()){
            if(evt.getStateChange() == ItemEvent.DESELECTED)
            {
                baseVolumePreviousItem =  evt.getItem().toString();
            }
            else if(evt.getStateChange() == ItemEvent.SELECTED)
            {
                baseVolumeNewItem =  evt.getItem().toString();
            }
        }
    }

    private void baseVolumeUnitComboBoxActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        String volumeOfBase = baseVolumeTextfield.getText();
        String convertedValue;
        if (!volumeOfBase.isEmpty() && baseVolumePreviousItem != null && baseVolumeNewItem !=null) {

            try {
                convertedValue = String.valueOf(Converter.convert(baseVolumePreviousItem, baseVolumeNewItem, Double.parseDouble(volumeOfBase)));
            } catch (NumberFormatException e) {
                notificationLabel.setText("Volume of base must be number");
                notificationPanel.setBackground(Color.red);
                notificationPanel.setVisible(true);
                return;
            }
            baseVolumeTextfield.setText(convertedValue);

        }
    }


}
