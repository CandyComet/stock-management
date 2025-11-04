package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {
    public static JComponent j;
    static ApiHandler ap = new ApiHandler();
    static File file;
    static String imagePath = "http://localhost:8080/images/";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showLoginWindow);
    }

    static void showLoginWindow() {
        JFrame f = new JFrame("Admin Login");
        f.setSize(400, 220);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emailL = new JLabel("Email:");
        JLabel passL = new JLabel("Password:");
        JTextField emailF = new JTextField();
        JPasswordField passF = new JPasswordField();
        JButton loginBtn = new JButton("LOGIN");

        emailL.setBounds(50, 40, 100, 25); emailF.setBounds(150, 40, 180, 25);
        passL.setBounds(50, 80, 100, 25); passF.setBounds(150, 80, 180, 25);
        loginBtn.setBounds(150, 130, 100, 30);

        f.add(emailL); f.add(emailF); f.add(passL); f.add(passF); f.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String email = emailF.getText().trim();
            String pass = new String(passF.getPassword()).trim();
            if(email.isEmpty() || pass.isEmpty()){
                JOptionPane.showMessageDialog(f, "Enter both fields!");
                return;
            }
            if(ap.validateLogin(email, pass)){
                f.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(f, "Invalid login!");
                emailF.setText("");
                passF.setText("");
            }
        });
        f.setVisible(true);
    }

    static void showMainMenu() {
        JFrame f = new JFrame("Main Menu");
        f.setSize(900, 600);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("STOCK MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(250, 40, 500, 40);
        f.add(title);

        JButton addStock = new JButton("Add Stock");
        JButton manageAdmin = new JButton("Manage Admins");
        JButton viewStock = new JButton("View Stock");
        JButton searchStock = new JButton("Search Stock");
        JButton imageBtn = new JButton("View Product Images");
        JButton exitBtn = new JButton("Exit");

        addStock.setBounds(150, 150, 200, 40);
        manageAdmin.setBounds(400, 150, 200, 40);
        viewStock.setBounds(150, 230, 200, 40);
        searchStock.setBounds(400, 230, 200, 40);
        imageBtn.setBounds(150, 310, 450, 40);
        exitBtn.setBounds(300, 400, 150, 40);

        f.add(addStock); f.add(manageAdmin); f.add(viewStock);
        f.add(searchStock); f.add(imageBtn); f.add(exitBtn);

        addStock.addActionListener(e -> { f.dispose(); showAddProductWindow(); });
        manageAdmin.addActionListener(e -> { f.dispose(); showAdminListWindow(); });
        viewStock.addActionListener(e -> { f.dispose(); showStockListWindow(); });
        searchStock.addActionListener(e -> { f.dispose(); showSearchWindow(); });
        imageBtn.addActionListener(e -> { f.dispose(); showImageFrame(); });
        exitBtn.addActionListener(e -> System.exit(0));

        f.setVisible(true);
    }

    static void showImageFrame() {
        JFrame f = new JFrame("Product Images");
        f.setSize(800, 500);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lb = new JLabel();
        lb.setBounds(10, 10, 500, 100);
        f.add(lb);

        JButton jb = new JButton("Select File");
        jb.setBounds(10, 130, 150, 30);
        f.add(jb);

        try {
            URL imageUrl = new URL(imagePath + "dog.png");
            ImageIcon imgIc = new ImageIcon(imageUrl);
            if (imgIc.getIconWidth() <= -1) {
                lb.setText("Image not found here..");
            } else {
                Image img = imgIc.getImage();
                Image resImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lb.setIcon(new ImageIcon(resImg));
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }

        jb.addActionListener(e -> showMenu());

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(650, 400, 100, 35);
        f.add(backBtn);

        backBtn.addActionListener(e -> {
            f.dispose();
            showMainMenu();
        });

        f.setVisible(true);
    }

    static void showMenu() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Only Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int res = fileChooser.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            ApiHandler.SaveImageFile(file);
            JOptionPane.showMessageDialog(null, "Image uploaded successfully!");
        }
    }

    static void showAddProductWindow() {
        JFrame f = new JFrame("Add Product");
        f.setSize(720, 250);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameL = new JLabel("Product Name");
        JLabel priceL = new JLabel("Product Price");
        JLabel qtyL = new JLabel("Product Quantity");
        JLabel whL = new JLabel("Warehouse Name");

        JTextField nameF = new JTextField();
        JTextField priceF = new JTextField();
        JTextField qtyF = new JTextField();
        JTextField whF = new JTextField();

        JButton saveBtn = new JButton("SAVE");
        JButton backBtn = new JButton("Back");

        nameL.setBounds(50, 30, 120, 25); nameF.setBounds(180, 30, 150, 25);
        priceL.setBounds(350, 30, 120, 25); priceF.setBounds(480, 30, 100, 25);
        qtyL.setBounds(50, 80, 120, 25); qtyF.setBounds(180, 80, 150, 25);
        whL.setBounds(350, 80, 120, 25); whF.setBounds(480, 80, 100, 25);
        saveBtn.setBounds(150, 140, 100, 30);
        backBtn.setBounds(280, 140, 100, 30);

        f.add(nameL); f.add(nameF);
        f.add(priceL); f.add(priceF);
        f.add(qtyL); f.add(qtyF);
        f.add(whL); f.add(whF);
        f.add(saveBtn); f.add(backBtn);

        saveBtn.addActionListener(e -> {
            try {
                String name = nameF.getText().trim();
                double price = Double.parseDouble(priceF.getText().trim());
                int qty = Integer.parseInt(qtyF.getText().trim());
                String wh = whF.getText().trim();
                StockModel s = new StockModel(0, name, price, wh, qty);
                boolean ok = ap.saveStock(s);
                if (ok) {
                    JOptionPane.showMessageDialog(f, "Product saved successfully!");
                    nameF.setText(""); priceF.setText(""); qtyF.setText(""); whF.setText("");
                } else {
                    JOptionPane.showMessageDialog(f, "Failed to save product!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Invalid input!");
            }
        });

        backBtn.addActionListener(e -> { f.dispose(); showMainMenu(); });
        f.setVisible(true);
    }

    static void showStockListWindow() {
        JFrame jf = new JFrame("Stock List");
        jf.setSize(820, 480);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Name"); JLabel l2 = new JLabel("Price");
        JLabel l3 = new JLabel("Warehouse"); JLabel l4 = new JLabel("Quantity");
        Color cyan = new Color(0,255,255);
        l1.setOpaque(true); l2.setOpaque(true); l3.setOpaque(true); l4.setOpaque(true);
        l1.setBackground(cyan); l2.setBackground(cyan);
        l3.setBackground(cyan); l4.setBackground(cyan);
        l1.setBounds(50,20,150,25);
        l2.setBounds(220,20,100,25);
        l3.setBounds(340,20,200,25);
        l4.setBounds(560,20,100,25);
        jf.add(l1); jf.add(l2); jf.add(l3); jf.add(l4);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        List<StockModel> all = ap.getAll();
        int startY = 0;
        for (StockModel s : all) {
            JPanel row = createRowPanel(s, panel, jf);
            row.setBounds(0, startY, 780, 40);
            panel.add(row);
            startY += 45;
        }

        panel.setPreferredSize(new Dimension(780, Math.max(200, startY)));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBounds(10,50,780,340);
        jf.add(scroll);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20,400,100,30);
        jf.add(backBtn);

        backBtn.addActionListener(e -> { jf.dispose(); showMainMenu(); });
        jf.setVisible(true);
    }

    private static JPanel createRowPanel(StockModel s, JPanel parentPanel, JFrame parentFrame) {
        JPanel row = new JPanel();
        row.setLayout(null);
        JTextField t1 = new JTextField(s.getProductName());
        JTextField t2 = new JTextField(String.valueOf(s.getProductPrice()));
        JTextField t3 = new JTextField(s.getWarehouseName());
        JTextField t4 = new JTextField(String.valueOf(s.getProductQuantity()));
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        t1.setBounds(50,5,150,25);
        t2.setBounds(220,5,100,25);
        t3.setBounds(340,5,200,25);
        t4.setBounds(560,5,100,25);
        edit.setBounds(670,5,70,25);
        del.setBounds(745,5,70,25);
        row.add(t1); row.add(t2); row.add(t3); row.add(t4); row.add(edit); row.add(del);

        int id = s.getId();
        del.addActionListener(e -> {
            boolean ok = ap.deleteStock(id);
            if (ok) {
                Container p = row.getParent();
                p.remove(row);
                p.revalidate();
                p.repaint();
            } else JOptionPane.showMessageDialog(parentFrame, "Delete failed!");
        });

        edit.addActionListener(e -> {
            try {
                String n = t1.getText().trim();
                double pr = Double.parseDouble(t2.getText().trim());
                String wh = t3.getText().trim();
                int q = Integer.parseInt(t4.getText().trim());
                StockModel u = new StockModel(id, n, pr, wh, q);
                boolean ok = ap.updateStock(u);
                if(ok) JOptionPane.showMessageDialog(parentFrame, "Updated!");
                else JOptionPane.showMessageDialog(parentFrame, "Update failed!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid input!");
            }
        });
        return row;
    }

    static void showSearchWindow() {
        JFrame sf = new JFrame("Search Product");
        sf.setSize(500,250);
        sf.setLayout(null);
        sf.setLocationRelativeTo(null);
        sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel fieldL = new JLabel("Select Field");
        JLabel keywordL = new JLabel("Enter Keyword");
        String[] fields = {"id","name","warehouse","quantity","price"};
        JComboBox<String> fieldBox = new JComboBox<>(fields);
        JTextField keywordF = new JTextField();
        JButton searchBtn = new JButton("Search");
        JButton backBtn = new JButton("Back");

        fieldL.setBounds(50,30,120,25);
        fieldBox.setBounds(180,30,200,25);
        keywordL.setBounds(50,80,120,25);
        keywordF.setBounds(180,80,200,25);
        searchBtn.setBounds(100,140,100,30);
        backBtn.setBounds(220,140,100,30);

        sf.add(fieldL); sf.add(fieldBox);
        sf.add(keywordL); sf.add(keywordF);
        sf.add(searchBtn); sf.add(backBtn);

        searchBtn.addActionListener(e -> {
            String field = (String) fieldBox.getSelectedItem();
            String keyword = keywordF.getText().trim();
            if(!keyword.isEmpty()){
                sf.dispose();
                showSearchResultWindow(field, keyword);
            }
        });

        backBtn.addActionListener(e -> { sf.dispose(); showMainMenu(); });
        sf.setVisible(true);
    }

    static void showSearchResultWindow(String field, String keyword) {
        JFrame rf = new JFrame("Search Results");
        rf.setSize(820,480);
        rf.setLayout(null);
        rf.setLocationRelativeTo(null);
        rf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Name"); JLabel l2 = new JLabel("Price");
        JLabel l3 = new JLabel("Warehouse"); JLabel l4 = new JLabel("Quantity");
        Color cyan = new Color(0,255,255);
        l1.setOpaque(true); l2.setOpaque(true); l3.setOpaque(true); l4.setOpaque(true);
        l1.setBackground(cyan); l2.setBackground(cyan);
        l3.setBackground(cyan); l4.setBackground(cyan);
        l1.setBounds(50,20,150,25);
        l2.setBounds(220,20,100,25);
        l3.setBounds(340,20,200,25);
        l4.setBounds(560,20,100,25);
        rf.add(l1); rf.add(l2); rf.add(l3); rf.add(l4);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        List<StockModel> results = ap.searchByField(field, keyword);
        int startY=0;
        for(StockModel s: results){
            JPanel row = createRowPanel(s,panel,rf);
            row.setBounds(0,startY,780,40);
            panel.add(row);
            startY+=45;
        }

        panel.setPreferredSize(new Dimension(780,Math.max(200,startY)));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBounds(10,50,780,340);
        rf.add(scroll);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20,400,100,30);
        rf.add(backBtn);
        backBtn.addActionListener(e -> { rf.dispose(); showSearchWindow(); });
        rf.setVisible(true);
    }

    static void showAdminListWindow() {
        JFrame jf = new JFrame("Admin List");
        jf.setSize(800,400);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Username"); JLabel l2 = new JLabel("Email");
        JLabel l3 = new JLabel("Phone"); JLabel l4 = new JLabel("Password");
        Color cyan = new Color(0,255,255);
        l1.setOpaque(true); l2.setOpaque(true); l3.setOpaque(true); l4.setOpaque(true);
        l1.setBackground(cyan); l2.setBackground(cyan);
        l3.setBackground(cyan); l4.setBackground(cyan);
        l1.setBounds(20,20,150,25);
        l2.setBounds(190,20,150,25);
        l3.setBounds(360,20,150,25);
        l4.setBounds(530,20,150,25);
        jf.add(l1); jf.add(l2); jf.add(l3); jf.add(l4);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        List<AdminModel> admins = ap.getAllAdmins();
        int startY = 0;
        for(AdminModel a : admins){
            JPanel row = createAdminRow(a,panel,jf);
            row.setBounds(0, startY, 760, 40);
            panel.add(row);
            startY+=45;
        }

        panel.setPreferredSize(new Dimension(760, Math.max(200, startY)));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBounds(10,50,760,250);
        jf.add(scroll);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20,310,100,30);
        JButton addBtn = new JButton("Add Admin");
        addBtn.setBounds(150,310,120,30);
        jf.add(backBtn); jf.add(addBtn);

        backBtn.addActionListener(e -> { jf.dispose(); showMainMenu(); });
        addBtn.addActionListener(e -> { jf.dispose(); showAddAdminWindow(); });
        jf.setVisible(true);
    }

    private static JPanel createAdminRow(AdminModel a, JPanel parent, JFrame parentFrame){
        JPanel row = new JPanel();
        row.setLayout(null);

        JTextField t1 = new JTextField(a.getUsername());
        JTextField t2 = new JTextField(a.getEmail());
        JTextField t3 = new JTextField(a.getPhone());
        JTextField t4 = new JTextField(a.getPassword());
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        t1.setBounds(20,5,150,25);
        t2.setBounds(190,5,150,25);
        t3.setBounds(360,5,150,25);
        t4.setBounds(530,5,150,25);
        edit.setBounds(690,5,70,25);
        del.setBounds(760,5,70,25);
        row.add(t1); row.add(t2); row.add(t3); row.add(t4); row.add(edit); row.add(del);

        int id = a.getId();
        del.addActionListener(e -> {
            boolean ok = ap.deleteAdmin(id);
            if(ok){
                Container p = row.getParent();
                p.remove(row);
                p.revalidate();
                p.repaint();
            } else JOptionPane.showMessageDialog(parentFrame, "Delete failed!");
        });

        edit.addActionListener(e -> {
            String username = t1.getText().trim();
            String email = t2.getText().trim();
            String phone = t3.getText().trim();
            String pass = t4.getText().trim();
            AdminModel updated = new AdminModel(id, username, email, phone, pass);
            boolean ok = ap.updateAdmin(updated);
            if(ok) JOptionPane.showMessageDialog(parentFrame, "Updated!");
            else JOptionPane.showMessageDialog(parentFrame, "Update failed!");
        });
        return row;
    }

    static void showAddAdminWindow(){
        JFrame f = new JFrame("Add Admin");
        f.setSize(500, 250);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nL = new JLabel("Username:");
        JLabel eL = new JLabel("Email:");
        JLabel pL = new JLabel("Phone:");
        JLabel passL = new JLabel("Password:");

        JTextField nF = new JTextField();
        JTextField eF = new JTextField();
        JTextField pF = new JTextField();
        JTextField passF = new JTextField();

        JButton saveBtn = new JButton("SAVE");
        JButton backBtn = new JButton("Back");

        nL.setBounds(50,30,100,25); nF.setBounds(160,30,200,25);
        eL.setBounds(50,60,100,25); eF.setBounds(160,60,200,25);
        pL.setBounds(50,90,100,25); pF.setBounds(160,90,200,25);
        passL.setBounds(50,120,100,25); passF.setBounds(160,120,200,25);
        saveBtn.setBounds(100,170,100,30);
        backBtn.setBounds(230,170,100,30);

        f.add(nL); f.add(nF); f.add(eL); f.add(eF);
        f.add(pL); f.add(pF); f.add(passL); f.add(passF);
        f.add(saveBtn); f.add(backBtn);

        saveBtn.addActionListener(e -> {
            String username = nF.getText().trim();
            String email = eF.getText().trim();
            String phone = pF.getText().trim();
            String pass = passF.getText().trim();
            if(username.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()){
                JOptionPane.showMessageDialog(f, "Fill all fields!");
                return;
            }
            AdminModel admin = new AdminModel(0, username, email, phone, pass);
            boolean ok = ap.saveAdmin(admin);
            if(ok){
                JOptionPane.showMessageDialog(f, "Admin Added!");
                nF.setText(""); eF.setText(""); pF.setText(""); passF.setText("");
            } else JOptionPane.showMessageDialog(f, "Add Failed!");
        });

        backBtn.addActionListener(e -> { f.dispose(); showAdminListWindow(); });
        f.setVisible(true);
    }
}
