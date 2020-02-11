 package com.spring.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.spring.service.LogFilesService;

@Service
public class LogFilesServiceImpl implements LogFilesService {

	@Autowired
	private AmazonS3 s3client;

	@Value("${jsa.s3.bucket}")
	private String bucketname;

	@Value("${jsa.s3.prefix}")
	private String prefix;

	@Value("${jsa.s3.searchKey}")
	private String key;

	@Value("${jsa.s3.searchValue}")
	private String value;

	@Override
	public Set<String> logFiles() {
		String searchword = "<ns2:" + key + ">" + value + "</ns2:" + key + ">";
		Set<String> keys = new HashSet<String>();
		ObjectListing objectListing = s3client.listObjects(bucketname, prefix + "/");
		for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
			keys.add(os.getKey());
		}
		return searching(keys, searchword);
	}

	Set<String> containFiles = new HashSet<String>();

	@Override
	public Set<String> searching(Set<String> files, String searchKeyword) {
		for (String key : files) {

			S3Object s3object = s3client.getObject(bucketname, key);

			BufferedReader br = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
			try {
				String readline = br.readLine();
				while (readline != null) {
					if (readline.trim().contains(searchKeyword.trim())) {
						containFiles.add(key);
						System.out.println(key);
					}
					readline = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return containFiles;
	}

}
