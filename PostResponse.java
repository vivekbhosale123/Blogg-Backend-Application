package org.vdb.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostResponse {

	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private long totalElements;
	private boolean lastPage;
}
