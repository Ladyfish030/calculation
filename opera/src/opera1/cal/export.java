package cal;

import cal.CommonException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class export {
	public static void main (String[] args) {
	}

	//����ļ�
	public static void TextToFile(String strFilename, String strBuffer) {
		try {
			//�����ļ�
			File fileText = new File(strFilename);
			FileWriter fileWriter = new FileWriter(fileText);
			fileWriter.write(strBuffer);
			fileWriter.write("\n");
			//�ر�������
			fileWriter.close();
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//��ʽת��
	public static String listostring(ArrayList<String> list) {
		if(list == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		int i = 1;
		for(String string : list) {
			result.append("[" + i++ + "]" +  string+System.getProperty("line.separator"));

		}
		return result.toString();
	}

	//��ȡ�ļ�����
	public static ArrayList<String> getlines(String filename1) {
		File file = new File(filename1);
		ArrayList<String>Answer1=new ArrayList<String>();
		if(!file.exists()) {
			System.out.println("δ�ҵ�Ŀ���ļ���");//ָ��·���µ��ļ��������������δ�ҵ�Ŀ���ļ�
		}else {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String linetext=null;
			while ((linetext=br.readLine())!= null) {
				Answer1.add(linetext);
			}
			br.close();//�ر�������

		}catch (IOException ex) {
			ex.printStackTrace();
		}
		}
		return Answer1;
      }

	public static void compare(File answerFile, File exerciseFile) throws IOException {
		if (!exerciseFile.exists()) {
			throw new CommonException("��ϰ���ļ�������");
		}
		if (!answerFile.exists()) {
			throw new CommonException("���ļ�������");
		}
		// key����ţ�value�Ǵ�
		Map<Integer, String> exerciseMap = new HashMap<>();
		Map<Integer, String> answerMap = new HashMap<>();
		// �ԱȵĽ��
		List<Integer> rightRsult=new LinkedList<>();
		List<Integer>  errorRsult=new LinkedList<>();
		InputStreamReader exerciseIn = new InputStreamReader(new FileInputStream(exerciseFile.getAbsolutePath()), StandardCharsets.UTF_8);
		InputStreamReader answerIn = new InputStreamReader(new FileInputStream(answerFile.getAbsolutePath()), StandardCharsets.UTF_8);
		BufferedReader exerciseReader = new BufferedReader(exerciseIn);
		BufferedReader answerReader = new BufferedReader(answerIn);
		String string = null;
		// �洢����ϰ��
		while ((string = exerciseReader.readLine()) != null) {
			string = string.replaceAll(" +", "");
			string = string.replaceAll("\uFEFF", "");
			try{
				String TEXT=string.split("\\[")[1];
				String num=TEXT.split("]")[0];
				exerciseMap.put(Integer.valueOf(num), string.split("]")[1].split("=")[1]);
			}catch(ArrayIndexOutOfBoundsException e){
//				System.out.println("��ϰ�������ʽ����");
			}
		}
		while ((string = answerReader.readLine()) != null) {
			string = string.replaceAll(" +", "");
			string = string.replaceAll("\uFEFF", "");
			try{
				String TEXT=string.split("\\[")[1];
				String num=TEXT.split("]")[0];
				answerMap.put(Integer.valueOf(num), string.split("]")[1]);
			}catch(ArrayIndexOutOfBoundsException e){
//				System.out.println("�𰸸�ʽ����");
			}
		}
		exerciseReader.close();
		answerReader.close();

		// �Ƚϴ�
		for (int i = 1; i <= answerMap.size(); i++){
			if(exerciseMap.containsKey(i)){
				if(exerciseMap.get(i).equals(answerMap.get(i))){
					rightRsult.add(i);
				}else {
					errorRsult.add(i);
				}
			}
		}
		// ���ȽϽ���洢���ļ���
		File file=new File("Grade.txt");
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(" ");
		printWriter.print("Correct:��ȷ������"+rightRsult.size()+"(");
		System.out.print("Correct:��ȷ������"+rightRsult.size()+"(");
		for (int str: rightRsult) {
			printWriter.print(str+",");
			System.out.print(str+",");
		}
		printWriter.println(")");
		System.out.println(")");
		printWriter.print("Wrong:����������"+errorRsult.size()+"(");
		System.out.print("Wrong:����������"+errorRsult.size()+"(");
		for (int str: errorRsult) {
			printWriter.print(str+",");
			System.out.print(str+",");
		}
		printWriter.print(")");
		System.out.print(")");
		printWriter.flush();
		fileWriter.flush();
		printWriter.close();
		fileWriter.close();
		System.out.println("�Ƚ����,");
	}


}


