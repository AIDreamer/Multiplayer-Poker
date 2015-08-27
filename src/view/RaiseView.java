package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class RaiseView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					RaiseView frame = new RaiseView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RaiseView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JSlider slider = new JSlider();
		slider.setBounds(52, 145, 325, 31);
		slider.setPaintTicks(true);
		contentPane.add(slider);

		JLabel Money = new JLabel("$$$$$$$$$");
		Money.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Money.setBounds(170, 84, 100, 20);
		contentPane.add(Money);

		JLabel Ignorable_Text = new JLabel("Raise Amount");
		Ignorable_Text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Ignorable_Text.setBounds(150, 34, 150, 20);
		contentPane.add(Ignorable_Text);

		JButton Raise_Button = new JButton("Raise");
		Raise_Button.setBounds(45, 204, 89, 23);
		contentPane.add(Raise_Button);

		JButton Cancel_Button = new JButton("Cancel");
		Cancel_Button.setBounds(295, 204, 89, 23);
		contentPane.add(Cancel_Button);

		JTextPane LowerBound = new JTextPane();
		LowerBound.setBackground(SystemColor.menu);
		LowerBound.setText("low");
		LowerBound.setBounds(10, 145, 40, 20);
		contentPane.add(LowerBound);

		JTextPane txtpnHigh = new JTextPane();
		txtpnHigh.setBackground(SystemColor.menu);
		txtpnHigh.setText("high");
		txtpnHigh.setBounds(377, 145, 40, 20);
		contentPane.add(txtpnHigh);
	}
}
