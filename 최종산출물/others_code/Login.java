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
 *  메인함수가 있어서 가장먼저 실행되는 파일입니다.
	시작시 1.5초동안 로그인 창이 유지되며 그후에 다른 메인프레임이 열립니다.
	
*/

public class Login {

	private static JFrame frame;
	
	public static void main(String[] args) {
		
		// 프로그램 시작
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");  // 자바에서 제공하는 UI look and feel 사용
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frame = new JFrame();						// 메인프레임 생성
		frame.setResizable(false);					// 메인프레임 크기 조절 불가
		frame.setBackground(Color.WHITE);				// 메인프레임 배경색 설정
		frame.getContentPane().setBackground(Color.WHITE);		// 메인프레임 안의 패널을 얻어와 배경색 설정
		
		frame.setBounds(400, 165, 800, 520);				// 메인프레임 크기 설정
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));	// 메인프레임 안의 패널을 얻어와 레이아웃 설정 
		frame.setUndecorated(true);					// 메인프레임 제일 위에 있는 창 비활성화, 창 크기조절, 프로그램 닫기 막대기 장식 삭제
		
		JLabel lblNewLabel = new JLabel("");				// 라벨 생성
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);	// 라벨 안의 내용의 위치 정렬
		lblNewLabel.setBackground(Color.WHITE);				// 라벨의 배경색 설정
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\uBCC0\uC7AC\uCCA0\\Desktop\\20200614120052.png"));	// 라벨 안의 내용에 그림 넣기
		frame.getContentPane().add(lblNewLabel);			// 메인프레임 안의 패널을 얻어와 라벨 붙이기
	
		frame.setVisible(true);						// 메인프레임 보이게 하기
		
		try {
			Thread.sleep(1500);					// 로그인 창 1.5초 동안 지속하기
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JAVAmain jm = new JAVAmain();					// 로그인 창 이후에 볼 메인프레임 생성 
		frame.dispose();								// 메인프레임(로그인 창) 종료
		
	}

}


