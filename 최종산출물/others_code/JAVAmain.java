package javaaProgram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;
import javax.swing.UIManager;

public class JAVAmain {

	private JFrame frame;		// 메인 프레임 객체
	private JTable table;		// 메인 프레임에서 왼쪽 테이블 객체
	
	public JAVAmain() {
		initialize();	
	}

	private void initialize() {
		
		frame = new JFrame();		// 메인 프레임 생성
		frame.setTitle("\uACE0\uC5F4\uC790 \uC790\uB3D9 \uD0D0\uC9C0 \uC2DC\uC2A4\uD15C");		// 메인 프레임 타이틀(고열자 자동 감지 시스템) 설정
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();			// 메인 프레임 안의 패널 레이아웃 얻어오기
		frame.setBounds(0, 0, 1150, 820);														// 메인 프레임 크기 설정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);									// X버튼 눌러 닫으면 모든 쓰레드 닫으면서 프로그램 완전 종료시키기
		
		JLabel south = new JLabel("V 1.0.0");													// 라벨 생성
		south.setBorder(new EmptyBorder(0, 5, 0, 25));											// 간격조절
		south.setHorizontalAlignment(SwingConstants.TRAILING);									// 위치조절
		frame.getContentPane().add(south, BorderLayout.SOUTH);									// 메인프레임 안의 패널에 라밸달기
		
		JSplitPane center = new JSplitPane();													// 스플릿 패널 생성
		center.setDividerSize(3);																// 간격조절
		frame.getContentPane().add(center, BorderLayout.CENTER);								// 메인 프레임 패널에 스필릿 패널달기
		
		JPanel panel_1 = new JPanel();															// 패널 생성
		panel_1.setBorder(new EmptyBorder(17, 16, 0, 7));										// 간격조절
		center.setLeftComponent(panel_1);														// 스플릿 패널 왼쪽에 패널달기
		panel_1.setLayout(new GridLayout(0, 1, 0, 5));											// 패널에 레이아웃 설정하기
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane();											
		scrollPane_1.setPreferredSize(new Dimension(280, 0));
		panel_1.add(scrollPane_1);
		
		
		
		// 테이블 안의 목록 객체(2col, 51low)
		Object[][] ob = new Object[][] {
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
			{null, null},
		};
		
		
		
		table = new JTable();
		table.setPreferredSize(new Dimension(270, 800));
		table.setPreferredScrollableViewportSize(new Dimension(100, 0));
		
		// 테이블 목록 작성
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				" \uBC88\uD638", " \uC2DC\uAC04"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		
		scrollPane_1.setViewportView(table);
		table.setShowVerticalLines(true);
		table.setBackground(Color.WHITE);
		
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(18, 9, 1, 21));
		center.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 20, 15, 0));
		panel_2.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 20));
		
		JLabel side_label1 = new JLabel("");
		side_label1.setPreferredSize(new Dimension(300, 0));
		side_label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {			
				side_label1.setBorder(new LineBorder(Color.RED));		
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				side_label1.setBorder(new LineBorder(Color.RED));
			}	
			@Override
			public void mousePressed(MouseEvent e) {
				side_label1.setBorder(new LineBorder(Color.RED));	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				side_label1.setBorder(new LineBorder(Color.LIGHT_GRAY));	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				side_label1.setBorder(new LineBorder(Color.LIGHT_GRAY));	
			}
		});
		
		side_label1.setHorizontalAlignment(SwingConstants.CENTER);
		side_label1.setIconTextGap(0);
		side_label1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		side_label1.setOpaque(true);
		side_label1.setBackground(Color.WHITE);
		panel.add(side_label1);
		
		JLabel side_label2 = new JLabel("");
		side_label2.setIconTextGap(0);
		side_label2.setPreferredSize(new Dimension(300, 0));
		side_label2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		side_label2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {			
				side_label2.setBorder(new LineBorder(Color.RED));		
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				side_label2.setBorder(new LineBorder(Color.RED));
			}	
			@Override
			public void mousePressed(MouseEvent e) {
				side_label2.setBorder(new LineBorder(Color.RED));	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				side_label2.setBorder(new LineBorder(Color.LIGHT_GRAY));	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				side_label2.setBorder(new LineBorder(Color.LIGHT_GRAY));	
			}
		});
		side_label2.setOpaque(true);
		
		side_label2.setBackground(Color.WHITE);
		panel.add(side_label2);
		
		JLabel side_label3 = new JLabel("");
		side_label3.setIconTextGap(0);
		side_label3.setPreferredSize(new Dimension(300, 0));
		side_label3.setSize(30, 50);
		
		side_label3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {			
				side_label3.setBorder(new LineBorder(Color.RED));		
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				side_label3.setBorder(new LineBorder(Color.RED));
			}	
			@Override
			public void mousePressed(MouseEvent e) {
				side_label3.setBorder(new LineBorder(Color.RED));	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				side_label3.setBorder(new LineBorder(Color.LIGHT_GRAY));	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				side_label3.setBorder(new LineBorder(Color.LIGHT_GRAY));	
			}
		});
		side_label3.setOpaque(true);
		side_label3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		side_label3.setBackground(Color.WHITE);
		panel.add(side_label3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 0, 15, 0));
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel center_label = new JLabel();
		center_label.setPreferredSize(new Dimension(400, 0));
		center_label.setHorizontalAlignment(SwingConstants.CENTER);
		center_label.setRequestFocusEnabled(false);
		center_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		center_label.setBorder(new LineBorder(Color.LIGHT_GRAY));
		center_label.setOpaque(true);
		center_label.setBackground(Color.WHITE);
		panel_3.add(center_label);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator = new JSeparator();
		separator.setBorder(new EmptyBorder(0, 0, 17, 0));
		separator.setAlignmentY(0.0f);
		panel_4.add(separator, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(UIManager.getColor("Button.background"));
		panel_4.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton_1 = new JButton("\uC7A0\uC2DC \uBA48\uCDB0\uC8FC\uC138\uC694");		
		panel_5.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\uB2F4\uB2F9\uC790\uAC00 \uC62C \uB54C\uAE4C\uC9C0 \uB300\uAE30\uD574\uC8FC\uC138\uC694");
		panel_5.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("\uB4A4\uB85C \uBB3C\uB7EC\uB098\uC8FC\uC138\uC694");
		panel_5.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("\uAC04\uACA9\uC744 \uB450\uACE0 \uC785\uC7A5\uD574 \uC8FC\uC138\uC694");
		panel_5.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("\uC77C\uB82C\uB85C \uC785\uC7A5\uD574 \uC8FC\uC138\uC694");
		panel_5.add(btnNewButton_5);
		
		frame.setVisible(true);
		
		GEThread thrd = new GEThread(ob, table, center_label, side_label1, side_label2, side_label3, btnNewButton_1, btnNewButton_2, btnNewButton_3, btnNewButton_4, btnNewButton_5);
		thrd.start();
		
		
		
	}
	

}
