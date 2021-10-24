package cal;

import cal.CommonException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class export {
	public static void main (String[] args) {
	}

	//输出文件
	public static void TextToFile(String strFilename, String strBuffer) {
		try {
			//创建文件
			File fileText = new File(strFilename);
			FileWriter fileWriter = new FileWriter(fileText);
			fileWriter.write(strBuffer);
			fileWriter.write("\n");
			//关闭输入流
			fileWriter.close();
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//格式转换
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

	//读取文件内容
	public static ArrayList<String> getlines(String filename1) {
		File file = new File(filename1);
		ArrayList<String>Answer1=new ArrayList<String>();
		if(!file.exists()) {
			System.out.println("未找到目标文件。");//指定路径下的文件不存在则输出：未找到目标文件
		}else {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String linetext=null;
			while ((linetext=br.readLine())!= null) {
				Answer1.add(linetext);
			}
			br.close();//关闭输入流

		}catch (IOException ex) {
			ex.printStackTrace();
		}
		}
		return Answer1;
      }

	public static void compare(File answerFile, File exerciseFile) throws IOException {
		if (!exerciseFile.exists()) {
			throw new CommonException("练习答案文件不存在");
		}
		if (!answerFile.exists()) {
			throw new CommonException("答案文件不存在");
		}
		// key是题号，value是答案
		Map<Integer, String> exerciseMap = new HashMap<>();
		Map<Integer, String> answerMap = new HashMap<>();
		// 对比的结果
		List<Integer> rightRsult=new LinkedList<>();
		List<Integer>  errorRsult=new LinkedList<>();
		InputStreamReader exerciseIn = new InputStreamReader(new FileInputStream(exerciseFile.getAbsolutePath()), StandardCharsets.UTF_8);
		InputStreamReader answerIn = new InputStreamReader(new FileInputStream(answerFile.getAbsolutePath()), StandardCharsets.UTF_8);
		BufferedReader exerciseReader = new BufferedReader(exerciseIn);
		BufferedReader answerReader = new BufferedReader(answerIn);
		String string = null;
		// 存储的练习答案
		while ((string = exerciseReader.readLine()) != null) {
			string = string.replaceAll(" +", "");
			string = string.replaceAll("\uFEFF", "");
			try{
				String TEXT=string.split("\\[")[1];
				String num=TEXT.split("]")[0];
				exerciseMap.put(Integer.valueOf(num), string.split("]")[1].split("=")[1]);
			}catch(ArrayIndexOutOfBoundsException e){
//				System.out.println("练习答案输入格式有误");
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
//				System.out.println("答案格式有误");
			}
		}
		exerciseReader.close();
		answerReader.close();

		// 比较答案
		for (int i = 1; i <= answerMap.size(); i++){
			if(exerciseMap.containsKey(i)){
				if(exerciseMap.get(i).equals(answerMap.get(i))){
					rightRsult.add(i);
				}else {
					errorRsult.add(i);
				}
			}
		}
		// 将比较结果存储到文件中
		File file=new File("Grade.txt");
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(" ");
		printWriter.print("Correct:正确题数："+rightRsult.size()+"(");
		System.out.print("Correct:正确题数："+rightRsult.size()+"(");
		for (int str: rightRsult) {
			printWriter.print(str+",");
			System.out.print(str+",");
		}
		printWriter.println(")");
		System.out.println(")");
		printWriter.print("Wrong:错误题数："+errorRsult.size()+"(");
		System.out.print("Wrong:错误题数："+errorRsult.size()+"(");
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
		System.out.println("比较完成,");
	}


}


