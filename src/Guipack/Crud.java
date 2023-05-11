package Guipack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;


public class Crud {
    private JPanel MainPanel;
    private JComboBox Db_Where;
    private JButton Db_SelectGo;
    private JTable Db_Table;
    private JComboBox DB_Operator;
    private JTextField Db_WhereComm;
    private JTextField Form_tipo;
    private JTextField Form_desc;
    private JTextField Form_qtde;
    private JTextField Form_Preco;
    private JButton atualizarButton;
    private JButton inserirButton;
    private JButton limparButton2;
    private JButton LimparButton1;
    private JButton deleteButton;
    private JTextField Form_id;

    public Crud() {
        ShowTable();
        MainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Db_SelectGo.addActionListener(e -> ShowTable());
        Db_Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel Tb_click = (DefaultTableModel) Db_Table.getModel();
                String Tb_id = Tb_click.getValueAt(Db_Table.getSelectedRow(),0).toString();
                String Tb_tipo = Tb_click.getValueAt(Db_Table.getSelectedRow(),1).toString();
                String Tb_desc = Tb_click.getValueAt(Db_Table.getSelectedRow(),2).toString();
                String Tb_qtde = Tb_click.getValueAt(Db_Table.getSelectedRow(),3).toString();
                String Tb_preco = Tb_click.getValueAt(Db_Table.getSelectedRow(),4).toString();

                Form_id.setText(Tb_id);
                Form_tipo.setText(Tb_tipo);
                Form_desc.setText(Tb_desc);
                Form_qtde.setText(Tb_qtde);
                Form_Preco.setText(Tb_preco);

            }
        });
        LimparButton1.addActionListener(e -> {
            Form_tipo.setText("");
            Form_desc.setText("");
            Form_qtde.setText("");
            Form_Preco.setText("");
            Form_id.setText("");
        });
        limparButton2.addActionListener(e -> {
            Db_WhereComm.setText("");
            DB_Operator.setSelectedIndex(0);
            Db_Where.setSelectedIndex(0);
            ShowTable();
        });
        inserirButton.addActionListener(e -> {
            Connection conn = sqlconnect.getConnection();
            String sql = "INSERT INTO Produto(Tipo,Descrição,Qtde,Preço) VALUES (?,?,?,?)";
            int resposta = JOptionPane.showConfirmDialog(null,"Confirme a inserção dos dados:\n"
                    +"Tipo: "+Form_tipo.getText()+ "\nDescrição: " + Form_desc.getText() + "\nQuantidade: " +Form_qtde.getText() + "\nPreço " + Form_Preco.getText());
            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    int idcheck = Integer.parseInt(Form_id.getText());
                    if (idcheck >6 || idcheck <0){
                        JOptionPane.showMessageDialog(null,"Tipo Invalido!");
                        return;}
                    PreparedStatement stm = conn.prepareStatement(sql);
                    stm.setInt(1, Integer.parseInt(Form_tipo.getText()));
                    stm.setString(2, Form_desc.getText());
                    stm.setInt(3, Integer.parseInt(Form_qtde.getText()));
                    stm.setDouble(4, Double.parseDouble(Form_Preco.getText()));
                    stm.execute();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "Insert realizado com sucesso");
                    ShowTable();
                    conn.close();
                } catch (SQLException er) {
                    er.printStackTrace();
                }
            }
            else {JOptionPane.showMessageDialog(null,"Operação cancelada");}
        });
        atualizarButton.addActionListener(e -> {
            Connection conn = sqlconnect.getConnection();
            String sql = "UPDATE Produto SET Tipo = ? ,Descrição = ?,Qtde = ?,Preço = ? WHERE Id_Produto = ?";
            int resposta = JOptionPane.showConfirmDialog(null,"Confirme a atualização dos dados:\n"
                    +"Tipo: "+Form_tipo.getText()+ "\nDescrição: " + Form_desc.getText() + "\nQuantidade: " +Form_qtde.getText() + "\nPreço " + Form_Preco.getText());
            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    int idcheck = Integer.parseInt(Form_id.getText());
                    if (idcheck >6 || idcheck <0){
                        JOptionPane.showMessageDialog(null,"Tipo Invalido!");
                        return;}
                    PreparedStatement stm = conn.prepareStatement(sql);
                    stm.setInt(1, Integer.parseInt(Form_tipo.getText()));
                    stm.setString(2, Form_desc.getText());
                    stm.setInt(3, Integer.parseInt(Form_qtde.getText()));
                    stm.setDouble(4, Double.parseDouble(Form_Preco.getText()));
                    stm.setInt(5,Integer.parseInt(Form_id.getText()));
                    stm.execute();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "Update realizado com sucesso");
                    ShowTable();
                    conn.close();
                } catch (SQLException er) {
                    er.printStackTrace();
                }
            }
            else {JOptionPane.showMessageDialog(null,"Operação cancelada");}
        });
        deleteButton.addActionListener(e -> {
            Connection conn = sqlconnect.getConnection();
            String sql = "DELETE FROM Produto WHERE id_produto="+Form_id.getText();
            int resposta = JOptionPane.showConfirmDialog(null,"Confirme a exclusão dos dados:\n"
                    +"Tipo: "+Form_tipo.getText()+ "\nDescrição: " + Form_desc.getText() + "\nQuantidade: " +Form_qtde.getText() + "\nPreço " + Form_Preco.getText() + "\nId: " + Form_id.getText());
            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    if (Objects.equals(Form_id.getText(), "")){
                        JOptionPane.showMessageDialog(null,"Id Invalido!");
                        return;}
                    PreparedStatement stm = conn.prepareStatement(sql);
                    stm.execute();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso");
                    ShowTable();
                    conn.close();
                } catch (SQLException er) {
                    er.printStackTrace();
                }
            }
            else {JOptionPane.showMessageDialog(null,"Operação cancelada");}
        });
    }

    public void ShowTable(){
        Connection conn = sqlconnect.getConnection();
        Db_Table.setModel(new DefaultTableModel());
        if (Db_Where.getSelectedItem().equals("")){
            try {
                String sql = "SELECT * FROM Produto";
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                DefaultTableModel tbmodelo = (DefaultTableModel) Db_Table.getModel();
                String[] columnNames = {"Id Produto", "Id Tipo", "Descrição", "Quantidade em Estoque","Preço do Produto"};
                tbmodelo.setColumnIdentifiers(columnNames);

                while (rs.next()){
                    String id = rs.getString(1);
                    String Tipo = rs.getString(2);
                    String Descricao = rs.getString(3);
                    String Qtde = String.valueOf(rs.getInt(4));
                    String Preco = String.valueOf(rs.getDouble(5));
                    String[] tbData = {id,Tipo,Descricao,Qtde,Preco};
                    tbmodelo.addRow(tbData);
                }
                } catch (SQLException er) {
                    er.printStackTrace();
                }
        }else{
            try {
                String sql = "SELECT * FROM Produto Where "+Db_Where.getSelectedItem()+" "+DB_Operator.getSelectedItem()+" '"+Db_WhereComm.getText()+"'";
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                DefaultTableModel tbmodelo = (DefaultTableModel) Db_Table.getModel();
                String[] columnNames = {"Id Produto", "Id Tipo", "Descrição", "Quantidade em Estoque","Preço do Produto"};
                tbmodelo.setColumnIdentifiers(columnNames);

                while (rs.next()){
                    String id = rs.getString(1);
                    String Tipo = rs.getString(2);
                    String Descricao = rs.getString(3);
                    String Qtde = String.valueOf(rs.getInt(4));
                    String Preco = String.valueOf(rs.getDouble(5));
                    String[] tbData = {id,Tipo,Descricao,Qtde,Preco};
                    tbmodelo.addRow(tbData);
                }
                } catch (SQLException er) {
                    er.printStackTrace();
                }
    }
}

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme");
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        JFrame MainP = new JFrame("Crud Bd");
        MainP.setContentPane(new Crud().MainPanel);
        MainP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainP.setSize(1300,800);
        MainP.pack();
        MainP.setVisible(true);
    }
}
