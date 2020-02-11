package com.spring.service;

import java.util.Set;

public interface LogFilesService {

	public Set<String> logFiles();

	public Set<String> searching(Set<String> files, String searchKeyword);

}
