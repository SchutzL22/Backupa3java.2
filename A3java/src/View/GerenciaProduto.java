package View;

import DAO.ProdutoDAO;
import Model.Produto;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class GerenciaProduto extends JFrame {

    private JTable tabela;

    public GerenciaProduto() {
        initComponents();
        carregarDados();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Gerenciamento de Estoque");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTopo = new JPanel();
        panelTopo.setBackground(new Color(240, 240, 240));
        JLabel titulo = new JLabel("Lista de Produtos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelTopo.add(titulo);
        add(panelTopo, BorderLayout.NORTH);

        tabela = new JTable();
        tabela.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nome", "Descrição", "Preço", "Qtd"}
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        tabela.setRowHeight(25);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAtualizar.addActionListener(e -> carregarDados());
        
        panelBotoes.add(btnAtualizar);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarDados() {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        
        ProdutoDAO dao = new ProdutoDAO();
        for (Produto p : dao.getMinhaLista()) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                String.format("R$ %.2f", p.getPreco()),
                p.getQuantidade()
            });
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {}
        java.awt.EventQueue.invokeLater(() -> new GerenciaProduto().setVisible(true));
    }
}
