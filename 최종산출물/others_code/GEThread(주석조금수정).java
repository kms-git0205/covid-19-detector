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
			
			
			//--------------------------------- �Ʒ� 5�� �Լ��� �������Լ� ��ư�� �ܼ��� ������ ------------------------------------------
			// 00000001(2)�� �����͸� �����Ͽ� "��ø��� �ּ���" �����߻���Ű�� ������ �Լ� ��ư�� �����Ű��
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
			
			// 00000002(2)�� �����͸� �����Ͽ� "����ڰ� �� ������ ������ּ���" �����߻���Ű�� ������ �Լ� ��ư�� �����Ű��
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
			
			// 00000003(2)�� �����͸� �����Ͽ� "�ڷ� �������ּ���" �����߻���Ű�� ������ �Լ� ��ư�� �����Ű��
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
			
			// 00000004(2)�� �����͸� �����Ͽ� "������ �ΰ� �������ּ���" �����߻���Ű�� ������ �Լ� ��ư�� �����Ű��
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
			
			// 00000005(2)�� �����͸� �����Ͽ� "�Ϸķ� �������ּ���" �����߻���Ű�� ������ �Լ� ��ư�� �����Ű��		
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
			
		//--------------------------------------------- �Ʒ����� GEThread �۵� �ڵ� ------------------------------------------
			
			while(true) {
				
				try {
					socket = new Socket("211.207.30.130", 5050);		// ������ ��Ʈ��ũ �����ϴ� �ڵ�
				} catch (UnknownHostException e1) {
				} catch (IOException e1) {
					continue;
				}
				try {
					inst = new BufferedInputStream(socket.getInputStream());
				
				
				String str = "C:\\Users\\����ö\\Desktop\\test" + cnt + ".jpg";
				file = new File(str);
				file.createNewFile();
				
				outst = new FileOutputStream(file);				
				byte[] buf = new byte[1024];
				

								
				while(true) {
						
					int n = inst.read(buf);															
					System.out.println(n);
					
					if(n == -1) {			// ���ϳ��� �����ϸ� �ݺ����� ���������ϴ�.
						
						outst.close();							
						break;						
					}
					
					outst.write(buf, 0, n);
					outst.flush();
					
				}
				
				if(file.length() == 0) {		// ������ ũ�Ⱑ 0�̸� ������ ����� �ٽ� ������ �����մϴ�. �̰��� ���Ͽ��� �о�� �����Ͱ� �ϳ��� ������츦 ���� �����Դϴ�.
					file.delete();
					continue;
				}
						
				changeImage(str, 240, 190);	// str ����� �̹��� ������ 240 x 190 �ȼ��� �ٲپ� str����� �̹��� ���Ͽ� ���� ����ϴ�.
					
				SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");	// ���̺� ��¥ �ø������� ��ü			
				Calendar time = Calendar.getInstance(); 					// ���̺� ��¥ �ø������� ��ü								
				
				
				// �Ʒ��� if else �������� ���� ������ ���۹����� ���ʴ�� ���� ������ �ű�� �ڵ��Դϴ�.
				
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
					
				
				

				// ���� �����͸� ������ �����͸� �����մϴ�.
				ob[0][0] = 1;
				ob[0][1] = format1.format(time.getTime());
				
				cnt++;
				check++;
				
				// ���� �����͸� ������ �����͸� �����մϴ�.
				table.setModel(new DefaultTableModel(
						ob,
						new String[] {
							"\uBC88\uD638", "\uC2DC\uAC04"
						}
					));
				
				

				// ���� �����͸� 1�ʿ� �ѹ��� ���۹޽��ϴ�.
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
	







	// ���ڿ� str�� �ش��ϴ� ����� �̹���������  _width�� �ش��ϴ� �ʺ�, _height�� �ش��ϴ� ���̷� �ٽ� str��η��� ���Ϸ� �ٲߴϴ�.
	private void changeImage(String str, int _width, int _height) {
		
        String imgOriginalPath= str;           // ���� �̹��� ���ϸ�
        String imgTargetPath= str;   		   // �� �̹��� ���ϸ�
        String imgFormat = "jpg";              // �� �̹��� ����. jpg, gif ��
        
        
        int newWidth = _width;                 // ���� �� ����
        int newHeight = _height;               // ���� �� ����
        String mainPosition = "H";             // W:�����߽�, H:�����߽�, X:������ ��ġ��(��������)
        
        Image image;
        int imageWidth;
        int imageHeight;
        double ratio;
        int w;
        int h;
 
        try{
            // ���� �̹��� ��������
            image = ImageIO.read(new File(imgOriginalPath));
 
            // ���� �̹��� ������ ��������
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
 
            if(mainPosition.equals("W")){    // ���̱���
 
                ratio = (double)newWidth/(double)imageWidth;
                w = (int)(imageWidth * ratio);
                h = (int)(imageHeight * ratio);
 
            }else if(mainPosition.equals("H")){ // ���̱���
 
                ratio = (double)newHeight/(double)imageHeight;
                w = (int)(imageWidth * ratio);
                h = (int)(imageHeight * ratio);
 
            }else{ //������ (��������)
 
                w = newWidth;
                h = newHeight;
            }
 
            // �̹��� ��������
            // Image.SCALE_DEFAULT : �⺻ �̹��� �����ϸ� �˰��� ���
            // Image.SCALE_FAST    : �̹��� �ε巯�򺸴� �ӵ� �켱
            // Image.SCALE_REPLICATE : ReplicateScaleFilter Ŭ������ ��üȭ �� �̹��� ũ�� ���� �˰���
            // Image.SCALE_SMOOTH  : �ӵ����� �̹��� �ε巯���� �켱
            // Image.SCALE_AREA_AVERAGING  : ��� �˰��� ���
            Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
 
            // �� �̹���  �����ϱ�
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
