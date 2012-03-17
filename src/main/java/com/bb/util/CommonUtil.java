package com.bb.util;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	
	public final int IMG_WIDTH = 70;
	public final int IMG_HEIGHT = 22;
	public final String SEND_MAIL_AGAIN = "activateMail";
	public final String RESET_PASSWORD = "forgotPwd";
	
	@AutowiredLogger
    private Logger logger;
//	@Autowired
//    private ConfigurationsValueCache configurationsValueCache;
//	@Autowired
//	private MailServiceImpl mailServiceImpl;
//
//	/*
//	 * ȥ�����пո�
//	 */
//	public String replaceAllSpace(String str) {
//
//		str = str.replaceAll("\\s", "");
//
//		return str;
//	}
//	/*
//	 * �����ж϶�����ַ��Ƿ�Ϊ��
//	 */
//	public boolean isNullOrEmpty(Object obj) {
//		boolean result = false;
//		if(obj == null) {
//			result = true;
//		}else if(obj instanceof String && obj.toString().isEmpty()) {
//			result = true;
//		}
//
//		return result;
//	}
//
//	/*
//	 * md5���� 32λ
//	 */
//	public String encryptByMD5(String str) {
//
//		MessageDigest md5;
//		StringBuffer buffer = new StringBuffer();
//		int code;
//		byte[] b;
//
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//			b = md5.digest(str.getBytes());
//			for(int i=0;i<b.length;i++) {
//				code = b[i];
//				if(code < 0) {
//					code += 256;
//				}
//				if(code < 16) {
//					buffer.append("0");
//				}
//				buffer.append(Integer.toHexString(code).toUpperCase());
//			}
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return buffer.toString();
//	}
//
//	/*
//	 * base64����
//	 */
//	public String encryptByBase64(String str) {
//		BASE64Encoder encoder = new BASE64Encoder();
//		String result = "";
//
//		result = encoder.encodeBuffer(str.getBytes());
//
//		return result;
//	}
//
//	/*
//	 * base64����
//	 */
//	public String decryptByBase64(String str) {
//		BASE64Encoder encoder = new BASE64Encoder();
//		String result = "";
//
//		result = encoder.encodeBuffer(str.getBytes());
//
//		return result;
//	}
//
//	/*
//	 * �����ʼ�
//	 */
//	public void sendEmail(HttpServletRequest request, String mailType) {
//		String email = request.getParameter("email");
//		String emailContent = "";
//		String activationCode = "";
//		String from = this.getConfigValue(Constants.MAIL_FROM);
//		String subject = "";
//		StringBuffer url = new StringBuffer(this.getConfigValue(Constants.WEB_HOST) + "/activateRegistration.do?");
//		Person person = null;
//		if(this.isNullOrEmpty(email)) { //����ע���û�,ֱ�ӷ���
//			return;
//		}
//		//����ʼ���ַ�����û�
//		person = personDao.findByEmail(email);
//		if(this.isNullOrEmpty(person.getEmail())) { //����ѯ������˵������ע���û���ֱ�ӷ���
//			return;
//		}
//		if(this.SEND_MAIL_AGAIN.equals(mailType)) { //����
//
//			subject = "wheel4 ע�ἤ��";
//        }else if(this.RESET_PASSWORD.equals(mailType)) { //��������
//
//        	subject = "[wheel4]�һ�����ʻ�����";
//        }
//		activationCode = person.getRegistration().getActivationCode();
//
//		try {
//			emailContent = this.readEmailContent(request, mailType);
//		} catch (IOException e) {
//			this.logger.error("Error send email ", e);
//		}
//
//		//���������ʼ��߳�
//		Thread t = new Thread(new SendMailThread(email, from, subject, emailContent));
//		t.start();
//
//
//	}
//
//	/*
//	 * ��ȡ�ʼ�����
//	 */
//	public String readEmailContent(HttpServletRequest request, String mailType) throws  IOException{
//
//		StringBuffer url = new StringBuffer();
//		Person person = null;
//		String email =  request.getParameter("email");
//		String activationCode = "";
//		StringWriter writer = new StringWriter();
//		VelocityEngine engine = new VelocityEngine(); //ģ���������
//        Properties p = new Properties();
//        Template template = null;
//
//        String path = request.getSession().getServletContext().getRealPath(this.getConfigValue(Constants.MAIL_CONTENT_FILE_PATH));
//
//        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
//
//        person = personDao.findByEmail(email);
//        activationCode = person.getRegistration().getActivationCode();
//
//        try {
//            engine.init(p); // ����ģ���·��
//            template = engine.getTemplate("emailContent.vm", "GBK");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        VelocityContext ctx = new VelocityContext(); //���������Ķ���
//
//        ctx.put("email", email);
//
//        if(this.SEND_MAIL_AGAIN.equals(mailType)) { //����
//
//        	url.append(this.getConfigValue(Constants.WEB_HOST) + "/activateRegistration.do?");
//        	ctx.put("activateEmail", true);
//        	ctx.put("resetPwd", false);
//        }else if(this.RESET_PASSWORD.equals(mailType)) { //��������
//
//        	url.append(this.getConfigValue(Constants.WEB_HOST) + "/showResetPassword.do?");
//        	ctx.put("activateEmail", false);
//        	ctx.put("resetPwd", true);
//        }
//
//        //��email,������,ƴ������
//		url.append("email="+email);
//		url.append("&activationCode="+activationCode);
//
//		ctx.put("link", url.toString());
//
//
//
//		template.merge(ctx, writer);
//
//        //System.out.println(writer.toString());
//
//
//
//
//
//		return writer.toString();
//	}
//
//	/*
//	 * У�������Ƿ��ѱ�ע��
//	 */
//	public String validateEmailIsRegistered(HttpServletRequest request) {
//
//		String email = request.getParameter("email");
//    	Map<String, String> map = new HashMap<String, String>();
//    	Person person = null;
//		person = personDao.findByEmail(email);
//
//		//���Ϊ��˵������δ��ע��
//		if(this.isNullOrEmpty(person.getEmail())) {
//
//			map.put("success", "true");
//		}else {
//			map.put("success", "false");
//		}
//
//
//		return ExtJSHelper.getjsonFromMap(map);
//	}
//
//	/*
//	 * ���ر?�ύ�ɹ������ת��Ϣ
//	 */
//	public String getSuccessURLJson(String str, String url) {
//
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("success", str);
//		map.put("url", url);
//
//
//		return ExtJSHelper.getjsonFromMap(map);
//	}
//
//	/*
//	 * У����֤���Ƿ���ȷ
//	 */
//	public String validateCheckCode(HttpServletRequest request) {
//
//		Map<String, String> map = new HashMap<String, String>();
//		String sysCode = request.getSession().getAttribute("checkCode").toString(); //ϵͳ��ɵ���֤��
//		String newCode = request.getParameter("checkCode"); //�û��������֤��
//
//		//�Ƚ������Ƿ���ȷ
//		if(!this.isNullOrEmpty(newCode) && newCode.trim().equalsIgnoreCase(sysCode)) {
//			map.put("success", "true");
//		}else {
//			map.put("success", "false");
//
//		}
//
//		return ExtJSHelper.getjsonFromMap(map);
//	}
//
//
//	/*
//	 * ���ע�ἤ����
//	 */
//	public String generateActivationCode(String email) {
//
//		//ȡemail��ǰ6λ�ӵ�ǰʱ��ĺ�������Ϊ���������
//		String code = (email.length()>=6?email.substring(0, 6):email) + System.currentTimeMillis();
//		//����
//		code = Base64.encodeBytes(code.getBytes());
//
//		//����16λ������
//		return code.substring(0, 16);
//	}
//
//	/*
//	 * ���У����ͼƬ
//	 */
//	public String generateVerifyImage(HttpServletRequest request, HttpServletResponse response) {
//
//		//��������ͼƬ����,ָ��ͼƬ��С,ͼƬ����
//		BufferedImage img = new BufferedImage(this.IMG_WIDTH, this.IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
//
//		// �õ���ͼƬ�Ļ��ʶ���
//		Graphics g = img.getGraphics();
//
//		//ȡУ����
//		String verifyStr = generateVeriyCode();
//
//		//��������
//		Random random = new Random();
//
//		g.setColor(new Color(200, 150, 255));
//
//		//����ͼƬ��С��ͬ�ľ���
//		g.fillRect(0, 0, this.IMG_WIDTH, this.IMG_WIDTH);
//
//		g.setColor(Color.GRAY);
//
//		//���100��������
//		for (int i=0;i<80;i++) {
//			int x = random.nextInt(this.IMG_WIDTH);
//			int y = random.nextInt(this.IMG_WIDTH);
//			int xl = random.nextInt(12);
//			int yl = random.nextInt(12);
//			g.drawLine(x, y, x+xl, y+yl);
//		}
//		//���6λУ����
//		g.setColor(new Color(222, 33, 85));
//		int len = verifyStr.length();
//		for(int i=0;i<len;i++) {
//
//			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
//			g.drawString(verifyStr.charAt(i)+"", i*15+5, 19);
//		}
//		g.dispose();
//		try {
//			ImageIO.write(img, "JPG", response.getOutputStream());
//		} catch (IOException e) {
//			this.logger.error("Error verify code ", e);
//		}
//		return verifyStr;
//	}
//
//	/*
//	 * ���У����
//	 */
//	public String generateVeriyCode() {
//		int temp1;
//		double temp2;
//		StringBuffer verifyStr = new StringBuffer();
//		//��������
//		Random random = new Random();
//		//���6λУ����
//		for(int i=0;i<4;i++) {
//			temp1 = random.nextInt(10);
//			if(temp1%2 == 0) {
//				temp2 = random.nextDouble()*(90-65)+65;
//				verifyStr.append((char)new Double(Math.floor(temp2)).intValue());
//			}else {
//				verifyStr.append(temp1);
//			}
//		}
//		return verifyStr.toString();
//	}
//
//	/*
//	 * ������������ȡ����ֵ
//	 */
//	public String getConfigValue(String key) {
//		return this.configurationsValueCache.getParameterValue(key);
//	}
//
//	/*
//	 * �ʼ������߳���
//	 */
//	private class SendMailThread implements Runnable {
//		String email;
//		String from;
//		String subject;
//		String emailContent;
//
//
//		SendMailThread() {
//		}
//		SendMailThread(String email, String from, String subject, String emailContent) {
//			this.email = email;
//			this.from = from;
//			this.subject = subject;
//			this.emailContent = emailContent;
//		}
//
//		/*
//		 * �����߳�
//		 */
//		@Override
//		public void run() {
//			logger.info("send mail to " + this.email + " start ");
//			sendMail();
//			logger.info("send mail to " + this.email + " finish ");
//		}
//		private void sendMail() {
//			mailServiceImpl.send(email, from, subject, emailContent);
//		}
//	}
//
}
