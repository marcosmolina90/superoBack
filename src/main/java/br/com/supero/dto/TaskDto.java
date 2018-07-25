package br.com.supero.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.supero.model.Task;
import lombok.Data;

@Data
public class TaskDto {

	private long id;
	private long codigo;
	private String descricao;
	private String status;
	private Date dtConclusao;
	private Date dtInclusao;


	public static List<TaskDto> getListInstance(List<Task> task){
		List<TaskDto> dto = new ArrayList<>();
		if(task!=null) {
			task.stream().forEach(l -> dto.add(getInstance(l)));
		}
		return dto;
	}
	
	public static TaskDto getInstance(Task task) {
		TaskDto dto = new TaskDto();
		dto.setId(task.getId());
		dto.setCodigo(task.getCodigo());
		dto.setDescricao(task.getDescricao());
		dto.setStatus(task.getStatus().name());
		dto.setDtConclusao(task.getDtConclusao());
		dto.setDtInclusao(task.getDtInclusao());
		return dto;	
	}

}
