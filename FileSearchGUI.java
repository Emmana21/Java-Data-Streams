import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileSearchGUI extends JFrame {
    private JTextArea originalTextArea, filteredTextArea;
    private JTextField searchField;
    private JButton loadButton, searchButton, quitButton;
    private Path filePath;

    public FileSearchGUI() {
        setTitle("File Search GUI");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        originalTextArea = new JTextArea();
        originalTextArea.setEditable(false);
        JScrollPane originalScrollPane = new JScrollPane(originalTextArea);

        filteredTextArea = new JTextArea();
        filteredTextArea.setEditable(false);
        JScrollPane filteredScrollPane = new JScrollPane(filteredTextArea);

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.add(originalScrollPane);
        textPanel.add(filteredScrollPane);
        add(textPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        searchField = new JTextField(20);
        loadButton = new JButton("Load File");
        searchButton = new JButton("Search");
        searchButton.setEnabled(false);
        quitButton = new JButton("Quit");

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFile();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        inputPanel.add(new JLabel("Search String: "));
        inputPanel.add(searchField);
        inputPanel.add(loadButton);
        inputPanel.add(searchButton);
        inputPanel.add(quitButton);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().toPath();
            try {
                String fileContent = Files.readString(filePath);
                originalTextArea.setText(fileContent);
                searchButton.setEnabled(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchFile() {
        String searchString = searchField.getText();
        try (Stream<String> lines = Files.lines(filePath)) {
            StringBuilder filteredContent = new StringBuilder();
            lines.filter(line -> line.contains(searchString))
                    .forEach(filteredContent::append);
            filteredTextArea.setText(filteredContent.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error searching file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
            }
        ;

