package gestioneSpese;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main extends JFrame{
	 	private SpesaDAO spesaDAO;
	    private CategoriaDAO categoriaDAO;
	    private JTextArea outputReport, outputArea, outputAreaModifica, outputAreaElimina, outputAreaCerca;	     
	    private JTextField nomeSpesaField, importoField, dataField;
	    private JTextField nomeCategoriaField, descrizioneField, cercaField, IDEliminaCategoriaField, nomeCatModificaField, descrizioneModificaField;
	    private JTextField IDSpesaModificaField, nomeSpesaModificaField, importoModificaField, dataModificaField, idCategoriaModificaField;
	    private JTextField IDEliminaField;	   
	    private JComboBox<String> categoriaComboBox;
	    private JComboBox<String> categoriaComboBox1;
	    private JComboBox<String> categoriaComboBox2;
	    private Map<String, Integer> categorieMap;
	    private  List<Categoria> categorie;
	    
	    
	    public Main() {
	    	
	    	setUpDatabase();
	    	categoriaDAO = new CategoriaDAO();
	    	spesaDAO = new SpesaDAO();
	    	categorie = categoriaDAO.getAllCategorie();
	    	categorieMap = new HashMap<>();
	    	
	    	setTitle("Gestione Spese Personali");
	    	setSize(1000, 800);
	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	
	    	 JTabbedPane tabbedPane = new JTabbedPane();
	    	 
	    	 JPanel reportspesePanel = new JPanel();
	         reportspesePanel.setLayout(new BorderLayout());
	         setupReportSpesePanel(reportspesePanel);
	         tabbedPane.addTab("Report", reportspesePanel);

	         JPanel spesePanel = new JPanel();
	         spesePanel.setLayout(new BorderLayout());
	         setupSpesePanel(spesePanel);
	         tabbedPane.addTab("Spese", spesePanel);

	         JPanel categoriePanel = new JPanel();
	         categoriePanel.setLayout(new BorderLayout());
	         setupCategoriePanel(categoriePanel);
	         tabbedPane.addTab("Categorie", categoriePanel);
	         
	         JPanel modificaSpesePanel = new JPanel();
	         modificaSpesePanel.setLayout(new BorderLayout());
	         setupModificaSpesePanel(modificaSpesePanel);
	         tabbedPane.addTab("Modifica", modificaSpesePanel);
	         
	         JPanel eliminaSpesePanel = new JPanel();
	         eliminaSpesePanel.setLayout(new BorderLayout());
	        setupEliminaSpesePanel(eliminaSpesePanel);
	         tabbedPane.addTab("Elimina", eliminaSpesePanel);
	         
	         JPanel cercaSpesePanel = new JPanel();
	         cercaSpesePanel.setLayout(new BorderLayout());
	        setupCercaSpesePanel(cercaSpesePanel);
	         tabbedPane.addTab("Cerca Spese", cercaSpesePanel);
	     
	         add(tabbedPane);
	     }
	    
	    private void setUpDatabase() {
	        JPanel credentialsPanel = new JPanel(new GridLayout(3, 2));
	        JTextField userField = new JTextField();
	        JPasswordField passwordField = new JPasswordField();

	        credentialsPanel.add(new JLabel("Database Username:"));
	        credentialsPanel.add(userField);
	        credentialsPanel.add(new JLabel("Database Password:"));
	        credentialsPanel.add(passwordField);

	        int result = JOptionPane.showConfirmDialog(null, credentialsPanel, "Inserisci le Credenziali del tuo Database", JOptionPane.OK_CANCEL_OPTION);
	        if (result == JOptionPane.OK_OPTION) {
	            String user = userField.getText();
	            String password = new String(passwordField.getPassword());

	            DatabaseConnection.setCredentials(user, password);
	            DatabaseSetup.main(new String[]{user, password});	    
	        } else {
	            System.exit(0);
	        }
	    }
	    
	    private void setupReportSpesePanel(JPanel panel) {
	        outputReport = new JTextArea();
	        JScrollPane scrollPaneReport = new JScrollPane(outputReport);
	        panel.add(scrollPaneReport, BorderLayout.CENTER);
	        visualizzaSpeseReport();
	        Pdf pdf = new Pdf();

	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new GridLayout(1,2));
	        buttonPanel.setBackground(new Color(153, 204, 255));
	        
	       
	        JButton btnVisualizza = createStyledButton("Visualizza Spese", Color.ORANGE);
	        btnVisualizza.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaSpeseReport();	            
	            }
	        });
	        buttonPanel.add(btnVisualizza);	
	        

	        JButton btnSalva = createStyledButton("Salva Spese in PDF", Color.GRAY);
	        
	        btnSalva.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Simulazione di un tempo di elaborazione
	            	//Disabilita il pulsante durante il salvataggio
	                btnSalva.setEnabled(false); 
	                // Operazione di salvataggio PDF
	                SwingUtilities.invokeLater(new Runnable() {
	                    public void run() {
	                        try {
	                        	//Simulazione di un processo di salvataggio
	                            Thread.sleep(1500); 
	                        } catch (InterruptedException ex) {
	                            ex.printStackTrace();
	                        }
	                        pdf.salvaPdf(); // Chiamata al metodo di salvataggio PDF
	                        JOptionPane.showMessageDialog(null, "Pdf Creato con Successo", "Pdf", JOptionPane.INFORMATION_MESSAGE);
	                     //Riabilita il pulsante dopo il salvataggio
	                        btnSalva.setEnabled(true); 
	                    }
	                });
	            }
	        });
	        
	        buttonPanel.add(btnSalva);
	        panel.add(buttonPanel, BorderLayout.SOUTH);
	    }
	   
	    private void setupSpesePanel(JPanel panel) {	    	
	        outputArea = new JTextArea();
	        JScrollPane scrollPane = new JScrollPane(outputArea);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        JPanel inputPanel = new JPanel();
	        inputPanel.setLayout(new GridLayout(5, 2));
	        inputPanel.setBackground(new Color(204, 229, 255));

	        inputPanel.add(createStyledLabel("Nome Spesa:"));
	        nomeSpesaField = new JTextField();
	        inputPanel.add(nomeSpesaField);

	        inputPanel.add(createStyledLabel("Importo(€):"));
	        importoField = new JTextField();
	        inputPanel.add(importoField);

	        inputPanel.add(createStyledLabel("Data Spesa (yyyy-MM-dd):"));
	        dataField = new JTextField();
	        inputPanel.add(dataField);

	        inputPanel.add(createStyledLabel("Categoria:"));
	        categoriaComboBox = new JComboBox<>();
	        inputPanel.add(categoriaComboBox);

	        //categorieMap = new HashMap<>();
	        categorie = categoriaDAO.getAllCategorie();
	        for (Categoria c : categorie) {
	            categoriaComboBox.addItem(c.getNomeCategoria());
	            categorieMap.put(c.getNomeCategoria(), c.getIdCategoria());
	        }
	        
	        inputPanel.add(new JLabel());

	        JButton btnAggiungi = createStyledButton("Aggiungi Spesa", Color.GREEN);
	        btnAggiungi.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                aggiungiSpesa();
	                nomeSpesaField.setText("");
	                importoField.setText("");
	                dataField.setText("");
	          
	            }
	        });
	        inputPanel.add(btnAggiungi);

	        panel.add(inputPanel, BorderLayout.NORTH);

	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new FlowLayout());
	        buttonPanel.setBackground(new Color(153, 204, 255));

	        JButton btnVisualizza = createStyledButton("Visualizza Spese", Color.ORANGE);
	        btnVisualizza.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaSpese(outputArea);
	                nomeSpesaField.setText("");
	                importoField.setText("");
	                dataField.setText("");
	            }
	        });
	        buttonPanel.add(btnVisualizza);	   
	        panel.add(buttonPanel, BorderLayout.SOUTH);
	    }
	    
	    private void setupCategoriePanel(JPanel panel) {
	        JTextArea outputAreaCategoria = new JTextArea();
	        JScrollPane scrollPaneCategoria = new JScrollPane(outputAreaCategoria);
	        panel.add(scrollPaneCategoria, BorderLayout.CENTER);

	        JPanel inputPanelCategoria = new JPanel();
	        inputPanelCategoria.setLayout(new GridLayout(3, 2));
	        inputPanelCategoria.setBackground(new Color(255, 204, 204));

	        inputPanelCategoria.add(createStyledLabel("Nome Categoria:"));
	        nomeCategoriaField = new JTextField();
	        inputPanelCategoria.add(nomeCategoriaField);

	        inputPanelCategoria.add(createStyledLabel("Descrizione:"));
	        descrizioneField = new JTextField();
	        inputPanelCategoria.add(descrizioneField);
	        inputPanelCategoria.add(new JLabel());
	        
	        JButton btnAggiungiCategoria = createStyledButton("Aggiungi Categoria", Color.GREEN);
	        btnAggiungiCategoria.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                aggiungiCategoria(outputAreaCategoria);
	                nomeCategoriaField.setText("");
	                descrizioneField.setText("");	                
	            }	           
	        });	     
	        inputPanelCategoria.add(btnAggiungiCategoria);

	        panel.add(inputPanelCategoria, BorderLayout.NORTH);

	        JPanel buttonPanelCategoria = new JPanel();
	        buttonPanelCategoria.setLayout(new FlowLayout());
	        buttonPanelCategoria.setBackground(new Color(255, 153, 153));

	        JButton btnVisualizzaCategoria = createStyledButton("Visualizza Categorie", Color.ORANGE);
	        btnVisualizzaCategoria.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaCategorie(outputAreaCategoria);
	                nomeCategoriaField.setText("");
	                descrizioneField.setText("");
	            }
	        });
	        buttonPanelCategoria.add(btnVisualizzaCategoria);

	        panel.add(buttonPanelCategoria, BorderLayout.SOUTH);
	    }
	    
	    private void setupModificaSpesePanel(JPanel panel) {
	        outputAreaModifica = new JTextArea();
	        JScrollPane scrollPane = new JScrollPane(outputAreaModifica);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        JPanel inputPanel = new JPanel();
	        inputPanel.setLayout(new GridLayout(11, 2));
	        inputPanel.setBackground(new Color(255, 255, 204));

	        inputPanel.add(createStyledLabel("ID Spesa:"));
	        IDSpesaModificaField = new JTextField();
	        inputPanel.add(IDSpesaModificaField);

	        inputPanel.add(createStyledLabel("Nome Spesa:"));
	        nomeSpesaModificaField = new JTextField();
	        inputPanel.add(nomeSpesaModificaField);

	        inputPanel.add(createStyledLabel("Importo(€):"));
	        importoModificaField = new JTextField();
	        inputPanel.add(importoModificaField);

	        inputPanel.add(createStyledLabel("Data Spesa (yyyy-MM-dd):"));
	        dataModificaField = new JTextField();
	        inputPanel.add(dataModificaField);

	        inputPanel.add(createStyledLabel("Categoria:"));
	        categoriaComboBox1 = new JComboBox<>();
	        inputPanel.add(categoriaComboBox1);

	        categorie = categoriaDAO.getAllCategorie();
	        for (Categoria c : categorie) {
	            categoriaComboBox1.addItem(c.getNomeCategoria());
	        }
	        
	        inputPanel.add(new JLabel());
	        JButton btnModifica = createStyledButton("Modifica Spesa", Color.BLUE);
	        btnModifica.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                modificaSpesa();
	                IDSpesaModificaField.setText("");
	                nomeSpesaModificaField.setText("");
	                importoModificaField.setText("");
	                dataModificaField.setText("");	             
	            }
	        });
	        inputPanel.add(btnModifica);
	        
	        inputPanel.add(new JLabel());
	        inputPanel.add(new JLabel());
	       
	        inputPanel.add(createStyledLabel("Categoria:"));
	        categoriaComboBox2 = new JComboBox<>();
	        inputPanel.add(categoriaComboBox2);

	        //categorieMap = new HashMap<>();
	       // List<Categoria> categorie = categoriaDAO.getAllCategorie();
	        for (Categoria c : categorie) {
	            categoriaComboBox2.addItem(c.getNomeCategoria());
	            categorieMap.put(c.getNomeCategoria(), c.getIdCategoria());
	        }

	        inputPanel.add(createStyledLabel("Nome Categoria:"));
	        nomeCatModificaField = new JTextField();
	        inputPanel.add(nomeCatModificaField);

	        inputPanel.add(createStyledLabel("Descrizione Categoria:"));
	        descrizioneModificaField = new JTextField();
	        inputPanel.add(descrizioneModificaField);
	        inputPanel.add(new JLabel());
	        JButton btnModificaCat = createStyledButton("Modifica Categoria", Color.BLUE);
	        btnModificaCat.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                modificaCategoria();
	                nomeCatModificaField.setText("");
	                descrizioneModificaField.setText("");	                
	            }
	        });
	        inputPanel.add(btnModificaCat);
	        panel.add(inputPanel, BorderLayout.NORTH);
	        
	        JPanel buttonPanelModifica = new JPanel();
	        buttonPanelModifica.setLayout(new GridLayout(1,2));
	        buttonPanelModifica.setBackground(new Color(153, 204, 255));

	        JButton btnVisualizza = createStyledButton("Visualizza Spese", Color.ORANGE);
	        btnVisualizza.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaSpese(outputAreaModifica);
	            }
	        });
	        
	        JButton btnVisualizzaCategoria = createStyledButton("Visualizza Categorie", Color.ORANGE);
	        btnVisualizzaCategoria.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaCategorie(outputAreaModifica);
	                
	            }
	        });
	        buttonPanelModifica.add(btnVisualizzaCategoria);
	        buttonPanelModifica.add(btnVisualizza);	   
	        panel.add(buttonPanelModifica, BorderLayout.SOUTH);
	       
	    }

	    
	    private void setupEliminaSpesePanel(JPanel panel) {
	    	outputAreaElimina = new JTextArea();
	        JScrollPane scrollPane = new JScrollPane(outputAreaElimina);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        JPanel inputPanel = new JPanel();
	        inputPanel.setLayout(new GridLayout(2, 3));
	        inputPanel.setBackground(new Color(255, 204, 255));
	        
	        inputPanel.add(createStyledLabel("ID Spesa:"));
	        IDEliminaField = new JTextField();
	        inputPanel.add(IDEliminaField);
	        JButton btnElimina = createStyledButton("Elimina Spesa", Color.RED);
	        btnElimina.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	              eliminaSpesa();
	              IDEliminaField.setText(""); 
	            }
	        });
	        inputPanel.add(btnElimina);
	        
	        inputPanel.add(createStyledLabel("ID Categoria:"));
	        IDEliminaCategoriaField = new JTextField();
	        inputPanel.add(IDEliminaCategoriaField);
	        JButton btnEliminaCat = createStyledButton("Elimina Categoria", Color.RED);
	        btnEliminaCat.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		eliminaCategoria();
	        		IDEliminaCategoriaField.setText("");
	        	}
	        });
	        inputPanel.add(btnEliminaCat);

	        panel.add(inputPanel, BorderLayout.NORTH);
	        
	        JPanel buttonPanelElimina = new JPanel();
	        buttonPanelElimina.setLayout(new GridLayout(1,2));
	        buttonPanelElimina.setBackground(new Color(153, 204, 255));

	        JButton btnVisualizza = createStyledButton("Visualizza Spese", Color.ORANGE);
	        btnVisualizza.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaSpese(outputAreaElimina);
	            }
	        });
	        
	        JButton btnVisualizzaCategoria = createStyledButton("Visualizza Categorie", Color.ORANGE);
	        btnVisualizzaCategoria.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                visualizzaCategorie(outputAreaElimina);
	                
	            }
	        });
	        buttonPanelElimina.add(btnVisualizzaCategoria);
	        buttonPanelElimina.add(btnVisualizza);	   
	        panel.add(buttonPanelElimina, BorderLayout.SOUTH);

	    }

	    private void setupCercaSpesePanel(JPanel panel) {
	    	outputAreaCerca = new JTextArea();
	        JScrollPane scrollPaneCerca = new JScrollPane(outputAreaCerca);
	        panel.add(scrollPaneCerca, BorderLayout.CENTER);

	        JPanel inputPanelCerca = new JPanel();
	        inputPanelCerca.setLayout(new GridLayout(2, 2));
	        inputPanelCerca.setBackground(new Color(204, 255, 204));
	        
	        inputPanelCerca.add(new JLabel("Cerca Spesa per Categoria o Data:"));
	        cercaField = new JTextField();
	        inputPanelCerca.add(cercaField);
	        inputPanelCerca.add(new JLabel());
	        
	        JButton btnCerca = new JButton("Cerca");
	        btnCerca.setBackground(Color.MAGENTA);
	        btnCerca.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String searchTerm = cercaField.getText();
	            cercaSpesa(searchTerm);
	            cercaField.setText("");
	            }
	        });
	        inputPanelCerca.add(btnCerca);

	        panel.add(inputPanelCerca, BorderLayout.NORTH);

	    }
	    
	    private JLabel createStyledLabel(String text) {
	        JLabel label = new JLabel(text);
	        label.setFont(new Font("Arial", Font.BOLD, 14));
	        return label;
	    }

	    private JButton createStyledButton(String text, Color color) {
	        JButton button = new JButton(text);
	        button.setBackground(color);
	        button.setForeground(Color.WHITE);
	        button.setFont(new Font("Arial", Font.BOLD, 14));
	        return button;
	    }
	    
	   
	    private void visualizzaSpese(JTextArea outputArea) {
	        java.util.List<Spesa> spese = spesaDAO.visualizzaAllSpese();
	        outputArea.setText("");
	        
	        for (Spesa s : spese) {
	            outputArea.append("ID Spesa: " + s.getIdSpesa() + " | Nome Spesa: " + s.getNomeSpesa() + " | Importo Spesa: " + s.getImporto() + "€" + " | Data: " + s.getData() + " | Categoria: " + s.getNome() + "\n");
	        }
	    }
	    
	    private void visualizzaSpeseReport() {
	        java.util.List<Spesa> spese = spesaDAO.visualizzaAllSpese();
	        outputReport.setText("");
	        
	        for (Spesa s : spese) {
	            outputReport.append("ID Spesa: " + s.getIdSpesa() + " | Nome Spesa: " + s.getNomeSpesa() + " | Importo Spesa: " + s.getImporto() + "€" + " | Data: " + s.getData() + " | Categoria: " + s.getNome() + "\n");
	        }
	    }
	   
	    private void aggiungiCategoria(JTextArea outputAreaCategoria) {
	        String nomeCategoria = nomeCategoriaField.getText().trim(); //rimuove spazi iniziali e finali vuoti
	        String descrizioneCategoria = descrizioneField.getText();

	        if (nomeCategoria.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Il campo Nome Categoria non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        Categoria categoria = new Categoria(nomeCategoria, descrizioneCategoria);
	        categoriaDAO.aggiungiCategoria(categoria);
	        JOptionPane.showMessageDialog(null, "Categoria Aggiunta con Successo", "Categoria", JOptionPane.INFORMATION_MESSAGE);
	        updateCategoriaComboBox();
	        updateCategoriaComboBox1();
	        updateCategoriaComboBox2();
	    }
	    
	    private void eliminaCategoria() {
	        int idCategoria = Integer.parseInt(IDEliminaCategoriaField.getText());
	        
	        // Verifica se l'ID della categoria è tra le prime 5 categorie
	        if (idCategoria <= 5) {
	            JOptionPane.showMessageDialog(null, "Non puoi eliminare le categorie preimpostate.", "Errore", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        if (categoriaDAO.speseAssociate(idCategoria)) {
	            JOptionPane.showMessageDialog(null, "Impossibile eliminare la categoria. Ci sono spese associate.", "Errore", JOptionPane.ERROR_MESSAGE);
	        } else {
	            categoriaDAO.eliminaCategoria(idCategoria);
	            JOptionPane.showMessageDialog(null, "Categoria Eliminata con Successo", "Categoria", JOptionPane.INFORMATION_MESSAGE);	            
	        }
	    }

	    private void visualizzaCategorie(JTextArea outputAreaCategoria) {
	        java.util.List<Categoria> categorie = categoriaDAO.visualizzaAllCategorie();
	        outputAreaCategoria.setText("");
	        for (Categoria c : categorie) {
	            outputAreaCategoria.append("ID Categoria: " + c.getIdCategoria() + "| Nome Categoria: " + c.getNomeCategoria() + " | Descrizione: " + c.getDescrizione() + "\n");
	        }
	    }
	    
	    private void modificaCategoria() {
	        String nomeCategoria = (String) categoriaComboBox.getSelectedItem();
	        int idCategoria = categorieMap.get(nomeCategoria);

	        // Verifica se l'ID della categoria è tra le prime 5 categorie
	        if (idCategoria <= 5) {
	            JOptionPane.showMessageDialog(null, "Non puoi modificare le categorie preimpostate.", "Errore", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        String nomeModificaCat = nomeCatModificaField.getText();
	        String descrizioneModifica = descrizioneModificaField.getText();
	        Categoria categoria = new Categoria(idCategoria, nomeModificaCat, descrizioneModifica);
	        categoriaDAO.modificaCategoria(categoria);
	        JOptionPane.showMessageDialog(null, "Categoria Modificata con Successo", "Categoria", 1, null);	       
	    }
	    
	    private void modificaSpesa() {
	    	int idSpesa = Integer.parseInt(IDSpesaModificaField.getText());
	    	String nomeModificaSpesa = nomeSpesaModificaField.getText();
	    	double importoModifica = Double.parseDouble(importoModificaField.getText());
	        Date dataModifica;
	        int idCategoriaModifica = Integer.parseInt(idCategoriaModificaField.getText());
	    	 try {
		            dataModifica = new SimpleDateFormat("yyyy-MM-dd").parse(dataModificaField.getText());
		            Spesa spesa = new Spesa(idSpesa, nomeModificaSpesa, dataModifica, importoModifica, idCategoriaModifica);
		            spesaDAO.modificaSpesa(spesa);
		            JOptionPane.showMessageDialog(null, "Spesa Modificata con Successo", "Spesa", 1, null);
		        } catch (ParseException e) {
		            outputAreaModifica.setText("Errore nel formato della data. Usa yyyy-MM-dd.\n");
		            JOptionPane.showMessageDialog(null, "Errore nel formato della data. Usa yyyy-MM-dd.", "Spesa", 0, null);		      
		        }	    
	    }
	    
	    private void eliminaSpesa() {
	    	 int idSpesa = Integer.parseInt(IDEliminaField.getText());
	         spesaDAO.eliminaSpesa(idSpesa); 
	        // outputAreaElimina.setText("Spesa eliminata con successo!\n");
	         JOptionPane.showMessageDialog(null, "Spesa Eliminata con Successo", "Spesa", 1, null);
	    }
	    
	  
	    private void aggiungiSpesa() {
	        String nomeSpesa = nomeSpesaField.getText();
	        double importo = Double.parseDouble(importoField.getText());
	        Date data;
	        String nomeCategoria = (String) categoriaComboBox.getSelectedItem();
	        int idCategoria = categorieMap.get(nomeCategoria);
	        try {
	            data = new SimpleDateFormat("yyyy-MM-dd").parse(dataField.getText());
	            Spesa spesa = new Spesa(nomeSpesa, data, importo, idCategoria);
	            spesaDAO.aggiungiSpesa(spesa);
	            JOptionPane.showMessageDialog(null, "Spesa Aggiunta con Successo", "Spesa", 1, null);
	        } catch (ParseException e) {
	            JOptionPane.showMessageDialog(null, "Errore nel formato della data. Usa yyyy-MM-dd.", "Spesa", 0, null);
	        }
	    }
	    
	    private void cercaSpesa(String searchTerm) {
	    	
	    	java.util.List<Spesa> spese = spesaDAO.cercaSpese(searchTerm);
	        outputAreaCerca.setText("");	        
	        for (Spesa s : spese) {	        	
	            outputAreaCerca.append("ID Spesa: " + s.getIdSpesa() + " | Nome Spesa: " + s.getNomeSpesa() + " | Importo Spesa: " + s.getImporto() + "€" + " | Data: " + s.getData() +
	            		"| ID Categoria: " + s.getIdCategoria() + " | Nome Categoria:" + s.getCategoria().getNomeCategoria() + " | Descrizione Categoria: " + s.getCategoria().getDescrizione() + "\n");
	        }
	        
	    }
	    
	    private void updateCategoriaComboBox() {
	        categoriaComboBox.removeAllItems();
	        categorieMap.clear();

	        List<Categoria> categorie = categoriaDAO.getAllCategorie();
	        for (Categoria c : categorie) {
	            categoriaComboBox.addItem(c.getNomeCategoria());
	            categorieMap.put(c.getNomeCategoria(), c.getIdCategoria());
	        }
	    }
	    private void updateCategoriaComboBox1() {
	        categoriaComboBox1.removeAllItems();
	        categorieMap.clear();

	        List<Categoria> categorie = categoriaDAO.getAllCategorie();
	        for (Categoria c : categorie) {
	            categoriaComboBox1.addItem(c.getNomeCategoria());
	            categorieMap.put(c.getNomeCategoria(), c.getIdCategoria());
	        }
	    }
	    private void updateCategoriaComboBox2() {
	        categoriaComboBox2.removeAllItems();
	        categorieMap.clear();

	        List<Categoria> categorie = categoriaDAO.getAllCategorie();
	        for (Categoria c : categorie) {
	            categoriaComboBox2.addItem(c.getNomeCategoria());
	            categorieMap.put(c.getNomeCategoria(), c.getIdCategoria());
	        }
	    }
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new Main().setVisible(true);
	            }
	        });
	    }
	    
}


