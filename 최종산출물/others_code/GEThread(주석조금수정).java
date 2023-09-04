package javaaProgram;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GEThread extends Thread {

	Object[][] ob;
	JTable table;
	JTabbedPane tp;
	JLabel center_label;
	JLabel side_label1;
	JLabel side_label2;
	JLabel side_label3;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	
	
	int check;
	int cnt;	
	int eof;
	float fl;
	
	Socket socket;
	BufferedInputStream inst;
	FileOutputStream outst;
	
	
	
	public GEThread(Object[][] ob, JTable table,
			JLabel center_label, JLabel side_label1, JLabel side_label2, JLabel side_label3, JButton button1, JButton button2, JButton button3,
			JButton button4, JButton button5) {	
		this.ob = ob;
		this.table = table;
		this.center_label = center_label;
		this.side_label1 = side_label1; 
		this.side_label2 = side_label2;
		this.side_label3 = side_label3;		
		this.button1 = button1;
		this.button2 = button2;
		this.button3 = button3;
		this.button4 = button4;
		this.button5 = button5;
		
		this.cnt = 0;
		this.check = 0;
	}

	
	
	@Override
	public void run() {
			
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
				
			File file = null;
			
			
			//--------------------------------- 아래 5개 함수는 리스너함수 버튼에 단순히 연결함 ------------------------------------------
			// 00000001(2)의 데이터를 전송하여 "잠시멈춰 주세요" 음성발생시키는 리스너 함수 버튼에 연결시키기
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Socket soc1;
					try {
						soc1 = new Socket("220.121.1.53", 1516);
						BufferedOutputStream out;
						out = new BufferedOutputStream(soc1.getOutputStream());
						out.write(1);
						out.flush();
						
						soc1.close();
						out.close();
											
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				}
				
			});
			
			// 00000002(2)의 데이터를 전송하여 "담당자가 올 때까지 대기해주세요" 음성발생시키는 리스너 함수 버튼에 연결시키기
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Socket soc1;
					try {
						soc1 = new Socket("220.121.1.53", 1516);
						BufferedOutputStream out;
						out = new BufferedOutputStream(soc1.getOutputStream());
						out.write(2);
						out.flush();
						
						soc1.close();
						out.close();
											
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				}
			});
			
			// 00000003(2)의 데이터를 전송하여 "뒤로 물러나주세요" 음성발생시키는 리스너 함수 버튼에 연결시키기
			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Socket soc1;
					try {
						soc1 = new Socket("220.121.1.53", 1516);
						BufferedOutputStream out;
						out = new BufferedOutputStream(soc1.getOutputStream());
						out.write(3);
						out.flush();
						
						soc1.close();
						out.close();
											
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				}
			});
			
			// 00000004(2)의 데이터를 전송하여 "간격을 두고 입장해주세요" 음성발생시키는 리스너 함수 버튼에 연결시키기
			button4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Socket soc1;
					try {
						soc1 = new Socket("220.121.1.53", 1516);
						BufferedOutputStream out;
						out = new BufferedOutputStream(soc1.getOutputStream());
						out.write(4);
						out.flush();
						
						soc1.close();
						out.close();
											
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}				
				}
			});
			
			// 00000005(2)의 데이터를 전송하여 "일렬로 입장해주세요" 음성발생시키는 리스너 함수 버튼에 연결시키기		
			button5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Socket soc1;
					try {
						soc1 = new Socket("220.121.1.53", 1516);
						BufferedOutputStream out;
						out = new BufferedOutputStream(soc1.getOutputStream());
						out.write(5);
						out.flush();
						
						soc1.close();
						out.close();
											
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				}
			});
			
		//--------------------------------------------- 아래부터 GEThread 작동 코드 ------------------------------------------
			
			while(true) {
				
				try {
					socket = new Socket("211.207.30.130", 5050);		// 서버와 네트워크 연결하는 코드
				} catch (UnknownHostException e1) {
				} catch (IOException e1) {
					continue;
				}
				try {
					inst = new BufferedInputStream(socket.getInputStream());
				
				
				String str = "C:\\Users\\변재철\\Desktop\\test" + cnt + ".jpg";
				file = new File(str);
				file.createNewFile();
				
				outst = new FileOutputStream(file);				
				byte[] buf = new byte[1024];
				

								
				while(true) {
						
					int n = inst.read(buf);															
					System.out.println(n);
					
					if(n == -1) {			// 파일끝에 도달하면 반복문을 빠져나갑니다.
						
						outst.close();							
						break;						
					}
					
					outst.write(buf, 0, n);
					outst.flush();
					
				}
				
				if(file.length() == 0) {		// 파일의 크기가 0이면 파일을 지우고 다시 서버에 연결합니다. 이경우는 소켓에서 읽어온 데이터가 하나도 없을경우를 위한 제어입니다.
					file.delete();
					continue;
				}
						
				changeImage(str, 240, 190);	// str 경로의 이미지 파일을 240 x 190 픽셀로 바꾸어 str경로의 이미지 파일에 덮어 씌웁니다.
					
				SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");	// 테이블에 날짜 올리기위한 객체			
				Calendar time = Calendar.getInstance(); 					// 테이블에 날짜 올리기위한 객체								
				
				
				// 아래의 if else 구문들은 고열자 사진을 전송받으면 차례대로 고열자 사진을 옮기는 코드입니다.
				
				if(side_label1.getIcon() != null && side_label2.getIcon() != null
						&& side_label3.getIcon() != null && center_label.getIcon() != null) {
					side_label3.setIcon(side_label2.getIcon());
					side_label2.setIcon(side_label1.getIcon());
					side_label1.setIcon(center_label.getIcon());					
					center_label.setIcon(new ImageIcon(str));		
				}
				
				if(center_label.getIcon() == null) {								
					center_label.setIcon(new ImageIcon(str));					
				}
				else if(side_label1.getIcon() == null) {
					
					side_label1.setIcon(center_label.getIcon());						
					center_label.setIcon(new ImageIcon(str));
				}
				else if(side_label2.getIcon() == null) {
					
					side_label2.setIcon(side_label1.getIcon());
					side_label1.setIcon(center_label.getIcon());	
					center_label.setIcon(new ImageIcon(str));
				}
				else if(side_label3.getIcon() == null){
					
					side_label3.setIcon(side_label2.getIcon());
					side_label2.setIcon(side_label1.getIcon());
					side_label1.setIcon(center_label.getIcon());			
					center_label.setIcon(new ImageIcon(str));					
				}
				
				for(int i=check; i>0; i--) {
					
					ob[i][0] = Integer.valueOf(ob[i-1][0].toString())+1;;
					ob[i][1] = ob[i-1][1];
					
				}
					
				
				

				// 고열자 데이터를 받으면 데이터를 수정합니다.
				ob[0][0] = 1;
				ob[0][1] = format1.format(time.getTime());
				
				cnt++;
				check++;
				
				// 고열자 데이터를 받으면 데이터를 수정합니다.
				table.setModel(new DefaultTableModel(
						ob,
						new String[] {
							"\uBC88\uD638", "\uC2DC\uAC04"
						}
					));
				
				

				// 고열자 데이터를 1초에 한번씩 전송받습니다.
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
			 catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			 }
			}
		
		
	}
	







	// 문자열 str에 해당하는 경로의 이미지파일을  _width에 해당하는 너비, _height에 해당하는 높이로 다시 str경로로의 파일로 바꿉니다.
	private void changeImage(String str, int _width, int _height) {
		
        String imgOriginalPath= str;           // 원본 이미지 파일명
        String imgTargetPath= str;   		   // 새 이미지 파일명
        String imgFormat = "jpg";              // 새 이미지 포맷. jpg, gif 등
        
        
        int newWidth = _width;                 // 변경 할 넓이
        int newHeight = _height;               // 변경 할 높이
        String mainPosition = "H";             // W:넓이중심, H:높이중심, X:설정한 수치로(비율무시)
        
        Image image;
        int imageWidth;
        int imageHeight;
        double ratio;
        int w;
        int h;
 
        try{
            // 원본 이미지 가져오기
            image = ImageIO.read(new File(imgOriginalPath));
 
            // 원본 이미지 사이즈 가져오기
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
 
            if(mainPosition.equals("W")){    // 넓이기준
 
                ratio = (double)newWidth/(double)imageWidth;
                w = (int)(imageWidth * ratio);
                h = (int)(imageHeight * ratio);
 
            }else if(mainPosition.equals("H")){ // 높이기준
 
                ratio = (double)newHeight/(double)imageHeight;
                w = (int)(imageWidth * ratio);
                h = (int)(imageHeight * ratio);
 
            }else{ //설정값 (비율무시)
 
                w = newWidth;
                h = newHeight;
            }
 
            // 이미지 리사이즈
            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
            // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
            // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
            // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
            Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
 
            // 새 이미지  저장하기
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImage, 0, 0, null);
            g.dispose();
            ImageIO.write(newImage, imgFormat, new File(imgTargetPath));
 
        }catch (Exception e){
 
            e.printStackTrace();
 
        }
		
		
	}
	
	
}
