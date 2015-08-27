package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainView extends JFrame {

	private final static Color BACKGROUND_COLOR = new Color(63, 89, 62);
	private final static Color TEXT_COLOR = new Color(250, 250, 250);
	private final static Color SEPARATOR_COLOR = new Color(88, 115, 83);

	private JPanel entirePanel;

	private JLabel curBetText;
	private JLabel potAmount;
	private JLabel raiseAmount;
	private JLabel notYourTurn;

	private JSlider raiseSlider;
	private TextArea updateBox;

	private ArrayList<JTextField> nameList = new ArrayList<JTextField>();
	private ArrayList<JLabel> moneyList = new ArrayList<JLabel>();
	private ArrayList<JLabel> betList = new ArrayList<JLabel>();
	private ArrayList<JButton> buttonList = new ArrayList<JButton>();
	private ArrayList<JLabel> card1List = new ArrayList<JLabel>();
	private ArrayList<JLabel> card2List = new ArrayList<JLabel>();
	private ArrayList<JLabel> tableList = new ArrayList<JLabel>();

	/**
	 * Create the frame.
	 */
	public MainView() {

		/** Create main view information */
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 601);

		/** Create the main panel */
		entirePanel = createMainPanel();
		setContentPane(entirePanel);

		/** Create Information for players */
		for (int i = 0; i < 10; i++) {
			/** Create textField that shows player name */
			JTextField playerName = createTextField(60 + 107 * i, 541, 93, 20);
			entirePanel.add(playerName);
			nameList.add(playerName);

			/** Create JLabels that show two cards */
			JLabel card1 = createCardLabel(80 + 107 * i, 428, 73, 100);
			card1List.add(card1);
			entirePanel.add(card1);

			JLabel card2 = createCardLabel(60 + 107 * i, 408, 73, 100);
			card2List.add(card2);
			entirePanel.add(card2);

			/** Create JLabel that shows money information */
			JLabel playerMoney = createCardText(60 + 107 * i, 376, 93, 20);
			entirePanel.add(playerMoney);
			moneyList.add(playerMoney);

			JLabel playerBet = createCardText(60 + 107 * i, 340, 93, 20);
			entirePanel.add(playerBet);
			betList.add(playerBet);
		}

		/** Create JSeparator for players */
		for (int i = 0; i < 9; i++) {
			JSeparator separator = createSeparator(160 + 107 * i, 401, 1, 150);
			entirePanel.add(separator);
		}

		/** Create chat box */
		JLabel updateLabel = createText(new Font("Tahoma", Font.PLAIN, 20),
				"Update Board", 30, 34, 235, 20);
		updateBox = createUpdateBox(30, 64, 235, 245);
		updateBox.setEditable(false);
		entirePanel.add(updateLabel);
		entirePanel.add(updateBox);

		/** Create table */
		JPanel table = createPanel(275, 180, 600, 100);
		entirePanel.add(table);

		/** Create table cards */
		for (int i = 0; i < 5; i++) {
			JLabel card = createCardLabel(100 + 100 * i, 0, 73, 97);
			table.add(card);
			tableList.add(card);
		}

		/** Create JSeparators for table cards */
		for (int i = 0; i < 4; i++) {
			JSeparator separator = createSeparator(186 + 100 * i, 401, 1, 150);
			table.add(separator);
		}

		/** Create playerPanel */
		JPanel playerPanel = createPanel(966, 25, 218, 267);
		entirePanel.add(playerPanel);

		/** Create three buttons */
		for (int i = 0; i < 3; i++) {
			JButton button = createButton("Start", 65, 150 + 45 * i, 89, 23);
			button.setVisible(false);
			playerPanel.add(button);
			buttonList.add(button);

			if (i == 1) {
				notYourTurn = new JLabel();
				notYourTurn.setHorizontalAlignment(SwingConstants.CENTER);
				notYourTurn.setForeground(TEXT_COLOR);
				notYourTurn.setText("Other Player's Turn");
				notYourTurn.setBackground(BACKGROUND_COLOR);
				notYourTurn.setBounds(65, 150 + 45 * i, 89, 23);
				notYourTurn.setVisible(false);
			}

		}
		buttonList.get(1).setVisible(true);

		/** Create texts in player panel */
		JLabel moneyText = createText(new Font("Tahoma", Font.PLAIN, 13),
				"Money to call:", 68, 31, 89, 20);
		JLabel raiseByText = createText(new Font("Tahoma", Font.PLAIN, 13),
				"Raise by:", 45, 110, 60, 20);
		curBetText = createText(new Font("Tahoma", Font.PLAIN, 13), "$$$", 68,
				50, 89, 20);
		playerPanel.add(moneyText);
		playerPanel.add(raiseByText);
		playerPanel.add(curBetText);

		/** Create the slider for raising */
		raiseSlider = createSlider(60, 90, 100, 20);
		raiseAmount = createText(null, "$", 120, 110, 100, 20);
		playerPanel.add(raiseSlider);
		playerPanel.add(raiseAmount);

		/** Create pot information text */
		JLabel potText = createText(new Font("Tahoma", Font.BOLD, 20),
				"POT VALUE", 520, 60, 200, 40);
		potAmount = createText(new Font("Tahoma", Font.BOLD, 20), "$   $", 520,
				100, 200, 40);
		entirePanel.add(potText);
		entirePanel.add(potAmount);
	}

	/**
	 * 
	 */
	private ImageIcon getScaledImage(Image imageIcon, int width, int height) {
		BufferedImage resizedImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImg.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(imageIcon, 0, 0, width, height, null);
		graphics.dispose();
		return new ImageIcon(resizedImg);
	}

	private JLabel createCardLabel(int x, int y, int width, int height) {
		JLabel label = new JLabel("Card Label");
		label.setIcon(new ImageIcon(getClass().getResource(".").getPath()
				+ "../controller/resources/FACE_DOWN.png"));
		label.setBounds(x, y, width, height);
		return label;
	}

	private JTextField createTextField(int x, int y, int width, int height) {
		JTextField playerName = new JTextField();
		playerName.setHorizontalAlignment(SwingConstants.CENTER);
		playerName.setBounds(x, y, width, height);
		playerName.setEditable(false);
		playerName.setColumns(10);
		return playerName;
	}

	private JLabel createCardText(int x, int y, int width, int height) {
		JLabel playerMoney = new JLabel();
		playerMoney.setHorizontalAlignment(SwingConstants.CENTER);
		playerMoney.setForeground(TEXT_COLOR);
		playerMoney.setText("");
		playerMoney.setBackground(BACKGROUND_COLOR);
		playerMoney.setBounds(x, y, width, height);
		return playerMoney;
	}

	private JSeparator createSeparator(int x, int y, int width, int height) {
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(SEPARATOR_COLOR);
		separator.setBounds(x, y, width, height);
		return separator;
	}

	private TextArea createUpdateBox(int x, int y, int width, int height) {
		TextArea updateBox = new TextArea();
		updateBox.setText("");
		updateBox.setEditable(false);
		updateBox.setBounds(x, y, width, height);
		return updateBox;
	}

	private JPanel createPanel(int x, int y, int width, int height) {
		JPanel table = new JPanel();
		table.setForeground(BACKGROUND_COLOR);
		table.setBackground(BACKGROUND_COLOR);
		table.setBounds(x, y, width, height);
		table.setLayout(null);
		return table;
	}

	private JButton createButton(String buttonText, int x, int y, int width,
			int height) {
		JButton button = new JButton(buttonText);
		button.setBounds(x, y, width, height);
		return button;
	}

	private JLabel createText(Font font, String text, int x, int y, int width,
			int height) {
		JLabel textLabel = new JLabel(text);
		if (font != null) {
			textLabel.setFont(font);
		}
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setToolTipText("");
		textLabel.setForeground(TEXT_COLOR);
		textLabel.setBackground(BACKGROUND_COLOR);
		textLabel.setBounds(x, y, width, height);
		return textLabel;
	}

	private JSlider createSlider(int x, int y, int width, int height) {
		JSlider slider = new JSlider();
		slider.setBounds(x, y, width, height);
		slider.setBackground(BACKGROUND_COLOR);
		return slider;
	}

	/**
	 * 
	 */
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(BACKGROUND_COLOR);
		mainPanel.setForeground(BACKGROUND_COLOR);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(null);
		return mainPanel;
	}

	/**
	 * Returns list of all the names
	 * 
	 * @return the nameList
	 */
	public ArrayList<JTextField> getNameList() {
		return nameList;
	}

	/**
	 * Returns the list of all buttons
	 * 
	 * @return
	 */
	public ArrayList<JButton> getButtonList() {
		return buttonList;
	}

	/**
	 * @return the moneyList
	 */
	public ArrayList<JLabel> getMoneyList() {
		return moneyList;
	}

	/**
	 * @return the potAmount
	 */
	public JLabel getPotAmount() {
		return potAmount;
	}

	/**
	 * @return the curBetText
	 */
	public JLabel getCurBetText() {
		return curBetText;
	}

	/**
	 * @return the card1List
	 */
	public ArrayList<JLabel> getCard1List() {
		return card1List;
	}

	/**
	 * @return the card2List
	 */
	public ArrayList<JLabel> getCard2List() {
		return card2List;
	}

	/**
	 * @return the tableList
	 */
	public ArrayList<JLabel> getTableList() {
		return tableList;
	}

	/**
	 * @return the betList
	 */
	public ArrayList<JLabel> getBetList() {
		return betList;
	}

	/**
	 * @return the raiseSlider
	 */
	public JSlider getRaiseSlider() {
		return raiseSlider;
	}

	/**
	 * @return the raiseAmount
	 */
	public JLabel getRaiseAmount() {
		return raiseAmount;
	}

	/**
	 * @return the updateBox
	 */
	public TextArea getUpdateBox() {
		return updateBox;
	}

	/**
	 * @return the notYourTurn
	 */
	public JLabel getNotYourTurn() {
		return notYourTurn;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
