package javaaProgram;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/*
 *  �����Լ��� �־ ������� ����Ǵ� �����Դϴ�.
	���۽� 1.5�ʵ��� �α��� â�� �����Ǹ� ���Ŀ� �ٸ� ������������ �����ϴ�.
	
*/

public class Login {

	private static JFrame frame;
	
	public static void main(String[] args) {
		
		// ���α׷� ����
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");  // �ڹٿ��� �����ϴ� UI look and feel ���
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frame = new JFrame();						// ���������� ����
		frame.setResizable(false);					// ���������� ũ�� ���� �Ұ�
		frame.setBackground(Color.WHITE);				// ���������� ���� ����
		frame.getContentPane().setBackground(Color.WHITE);		// ���������� ���� �г��� ���� ���� ����
		
		frame.setBounds(400, 165, 800, 520);				// ���������� ũ�� ����
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));	// ���������� ���� �г��� ���� ���̾ƿ� ���� 
		frame.setUndecorated(true);					// ���������� ���� ���� �ִ� â ��Ȱ��ȭ, â ũ������, ���α׷� �ݱ� ����� ��� ����
		
		JLabel lblNewLabel = new JLabel("");				// �� ����
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);	// �� ���� ������ ��ġ ����
		lblNewLabel.setBackground(Color.WHITE);				// ���� ���� ����
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\uBCC0\uC7AC\uCCA0\\Desktop\\20200614120052.png"));	// �� ���� ���뿡 �׸� �ֱ�
		frame.getContentPane().add(lblNewLabel);			// ���������� ���� �г��� ���� �� ���̱�
	
		frame.setVisible(true);						// ���������� ���̰� �ϱ�
		
		try {
			Thread.sleep(1500);					// �α��� â 1.5�� ���� �����ϱ�
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JAVAmain jm = new JAVAmain();					// �α��� â ���Ŀ� �� ���������� ���� 
		frame.dispose();								// ����������(�α��� â) ����
		
	}

}


