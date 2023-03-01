package ChemistryCalculator.frontend;

import ChemistryCalculator.backend.Compound;
import ChemistryCalculator.backend.InvalidAtomException;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PercentOfCompletionPanel extends JPanel {
    private static final Font SEGOE_UI = new Font("Segoe UI",  1, 14);
    private static final Color MAIN_COLOR = new Color(64, 43,  100);
    private static final Color GRAY = new Color(204, 204,  204);

    private  JLabel labelforcompoundTextfield = new JLabel();
    private  JTextField compoundTextfield = new JTextField();

    private  JButton getPercentOfCompletionButton = new JButton();
    private  JButton clearButton = new JButton();

    private  JScrollPane ansTableScrollPane = new JScrollPane();
    private  JTable ansTable = new JTable();
    private  JButton pieChartButton = new JButton();

    private  JPanel errorMessagePanel = new JPanel();
    private  JLabel errorMessageLabel = new JLabel();


    private DefaultTableModel dataTableModel;

    public PercentOfCompletionPanel() {
        initComponent();
        setComponentLayout();
    }

    private JLabel createLabel(String text, Font font, Color foregroundColor) {
        JLabel label = new JLabel();
        label.setFont(font);
        label.setForeground(foregroundColor);
        label.setText(text);
        return label;
    }

    private JButton createButton(String text, Font font, Color backgroundColor, Color foregroundColor, boolean isAutoscrolls, Cursor cursor, ActionListener actionListener) {
        JButton button = new JButton();
        button.setBackground(backgroundColor);
        button.setFont(font);
        button.setForeground(foregroundColor);
        button.setText(text);
        button.setAutoscrolls(isAutoscrolls);
        button.setCursor(cursor);
        button.addActionListener(actionListener);
        return button;
    }

    private JTable createTable(DefaultTableModel tableModel, Font font, Color backgroundColor, Color foregroundColor, boolean showVerticalLines) {
        JTable table = new JTable();
        table.setModel(tableModel);
        table.setBackground(backgroundColor);
        table.setBorder(BorderFactory.createEtchedBorder());
        table.setFont(font);
        table.setForeground(foregroundColor);
        table.setRowHeight(50);
        table.setShowVerticalLines(showVerticalLines);
        table.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        return table;
    }

    private JScrollPane createPanel(Color backgroundColor, boolean visible) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVisible(visible);
        scrollPane.setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        return scrollPane;
    }

    private JPanel createJPanel(Color color, boolean visible) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setVisible(visible);
        return panel;
    }

    private void initComponent() {
        labelforcompoundTextfield = createLabel("Enter Compound :", SEGOE_UI, MAIN_COLOR);

        getPercentOfCompletionButton = createButton("Percent of completion", SEGOE_UI, MAIN_COLOR, GRAY, true, new Cursor(Cursor.HAND_CURSOR), this::pcom_ans_buttonActionPerformed);

        ansTableScrollPane = createPanel(MAIN_COLOR, false);
        dataTableModel = new DefaultTableModel(new Object[][] {}, new String[] {});
        ansTable = createTable(dataTableModel, SEGOE_UI, MAIN_COLOR, GRAY, false);
        ansTableScrollPane.setViewportView(ansTable);

        pieChartButton = createButton("See pie chart", SEGOE_UI, MAIN_COLOR, GRAY, true, new Cursor(Cursor.HAND_CURSOR), this::pcom_pie_chart_buttonActionPerformed);
        pieChartButton.setVisible(false);

        errorMessagePanel = createJPanel(Color.red, false);
        errorMessageLabel = createLabel("", SEGOE_UI, Color.white);
        errorMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessagePanel.add(errorMessageLabel);

        clearButton = createButton("Clear", SEGOE_UI, MAIN_COLOR, GRAY, true, new Cursor(Cursor.HAND_CURSOR), this::pcom_clear_buttonActionPerformed);
    }



    private void setComponentLayout() {
        //error Message Panel Layout
        GroupLayout errorMessagePanelLayout = new GroupLayout(errorMessagePanel);
        errorMessagePanel.setLayout(errorMessagePanelLayout);
        errorMessagePanelLayout.setHorizontalGroup(
                errorMessagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, errorMessagePanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(errorMessageLabel, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE))
        );
        errorMessagePanelLayout.setVerticalGroup(
                errorMessagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(errorMessagePanelLayout.createSequentialGroup()
                                .addComponent(errorMessageLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        //main panel Layout
        GroupLayout Layout = new GroupLayout(this);
        this.setLayout(Layout);
        Layout.setHorizontalGroup(
                Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(Layout.createSequentialGroup()
                                .addComponent(pieChartButton)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(Layout.createSequentialGroup()
                                .addComponent(ansTableScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(errorMessagePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(Layout.createSequentialGroup()
                                                .addComponent(getPercentOfCompletionButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(clearButton))
                                        .addGroup(Layout.createSequentialGroup()
                                                .addComponent(labelforcompoundTextfield)
                                                .addGap(18, 18, 18)
                                                .addComponent(compoundTextfield, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(93, Short.MAX_VALUE))
        );
        Layout.setVerticalGroup(
                Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(errorMessagePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelforcompoundTextfield, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(compoundTextfield, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(getPercentOfCompletionButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addComponent(ansTableScrollPane, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pieChartButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(72, Short.MAX_VALUE))
        );

    }

    private void pcom_ans_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String compound_text = compoundTextfield.getText();
        if (!compound_text.isEmpty()) {
            Compound compound;
            try {
                compound = new Compound(compound_text);

            } catch (InvalidAtomException e) {
                errorMessagePanel.setVisible(true);
                errorMessageLabel.setText(e.getMessage());


                pieChartButton.setVisible(false);
                ansTableScrollPane.setVisible(false);
                return;
            }

            ArrayList<HashMap<String, String>> percentageOfCompletion = compound.getPercentageOfCompletion();
            String[] table_header = new String[]{"Name", "Symbol", "Total atoms", "Atomic mass", "Percentage"};
            String[][] table_row = percentageOfCompletion.stream()
                    .map(eachElement ->
                            new String[]{eachElement.get("name"),
                                    eachElement.get("symbol"),
                                    eachElement.get("totalAtoms"),
                                    String.format("%.2f", Double.parseDouble(eachElement.get("atomicMass"))),
                                    String.format("%.2f", Double.parseDouble(eachElement.get("massPercent")))
                            }
                    ).toArray(String[][]::new);
            dataTableModel.setDataVector(table_row, table_header
            );


            pieChartButton.setVisible(true);
            ansTableScrollPane.setVisible(true);
            errorMessagePanel.setVisible(false);

        } else {
            errorMessagePanel.setVisible(true);
            errorMessageLabel.setText("Please enter a compound.");

            pieChartButton.setVisible(false);
            ansTableScrollPane.setVisible(false);
        }


    }

    private void pcom_clear_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        ansTableScrollPane.setVisible(false);
        pieChartButton.setVisible(false);
        errorMessagePanel.setVisible(false);
        compoundTextfield.setText(null);
    }

    private void pcom_pie_chart_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        FxPieChart pieChart = new FxPieChart(dataTableModel.getDataVector());
        pieChart.toFront();
        pieChart.requestFocus();
    }


}
