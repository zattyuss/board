package com.jafa.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jafa.dao.BoardCatMapper;
import com.jafa.dto.BoardCatVO;

@Component
public class FileCheckTask {

	@Autowired
	private BoardCatMapper catMapper;
	
	
	@Scheduled(cron = "0 0 2 * * *")
	public void checkFile() {
		System.out.println("체크 파일....");
		List<BoardCatVO> fileList = catMapper.getOldFiles();
		
		List<Path> fileListPaths = fileList
				.stream()
				.map(vo -> Paths.get("C:\\storage", vo.getUploadPath(), "s_" + vo.getUuid()+"_"+vo.getFileName()))
				.collect(Collectors.toList());
		fileList
		.stream()
		.filter(vo -> vo.isFileType()==true)
		.map(vo -> Paths.get("C:\\storage", vo.getUploadPath(),"s_"+ vo.getUuid()+"_"+ vo.getFileName()))
		.forEach(p-> fileListPaths.add(p));
		
		File targetDir = Paths.get("C:/storage", getFolderYesterday()).toFile();
		File[] removeFiles
		= targetDir.listFiles(file -> fileListPaths.contains(file.toPath())==false);
		
		Arrays.asList(removeFiles).stream().forEach(f ->f.delete());
				
	}
	
	private String getFolderYesterday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return sdf.format(cal.getTime()).replace("-", File.separator);
	}
}
