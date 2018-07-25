package br.com.supero.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;

import br.com.supero.dto.TaskDto;
import br.com.supero.model.Task;
import br.com.supero.model.enums.EnumStatusTask;

@RequestScoped
public class TaskRB extends BaseRepository<Task, Long> {

	private static final long serialVersionUID = -4297085743672705144L;

	public Task create(TaskDto dto) {
		Task t = entityManager.find(Task.class, dto.getId());
		if(t == null) {
			t = new Task();
		}
		t.setCodigo(dto.getCodigo());
		t.setDescricao(dto.getDescricao());
		t.setStatus(EnumStatusTask.valueOf(dto.getStatus()));
		t.setDtConclusao(dto.getDtConclusao());
		t.setDtInclusao(dto.getDtInclusao());
		t= entityManager.merge(t);
		entityManager.flush();
		return t;
	}

	public List<Task> getter() {
		try {
			return entityManager.createQuery("select t from Task t ", Task.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

}
