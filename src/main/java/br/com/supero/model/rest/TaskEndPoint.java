package br.com.supero.model.rest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.GZIP;

import br.com.supero.dto.TaskDto;
import br.com.supero.model.Task;
import br.com.supero.model.enums.EnumDto;
import br.com.supero.model.enums.EnumStatusTask;
import br.com.supero.repository.TaskRB;
import br.com.supero.util.Util;

@RequestScoped
@Path("/task")
@Transactional
public class TaskEndPoint {

	@Inject
	private TaskRB taskRB;

	

	@POST
	@GZIP
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adicionarPagamentos(TaskDto dto)

	{
		try {
			Task task = taskRB.create(dto);
			return Response.ok(TaskDto.getInstance(task)).build();
		} catch (Exception ex) {
			return Response.ok("erro").build();
		}
	}

	@GET
	@GZIP
	@Path("/find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@Context UriInfo info) {
		Map<String, String> map = Util.parseFilter(info.getQueryParameters());
		Task t = taskRB.find(Long.parseLong(map.get("id")));
		if (t != null)
			return Response.ok(TaskDto.getInstance(t)).build();
		else
			return Response.ok().build();
	}

	@GET
	@GZIP
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getter(@Context UriInfo info) {
		List<Task> t = taskRB.getter();
		if (t != null)
			return Response.ok(TaskDto.getListInstance(t)).build();
		else
			return Response.ok().build();
	}
	
	
	@GET
	@GZIP
	@Path("/getStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus(@Context UriInfo info)
	{
		List<EnumDto> list = new ArrayList<>();
		for (EnumStatusTask en : EnumStatusTask.values())
		{
			EnumDto e = new EnumDto(String.valueOf(en), en.getDescricao());
			list.add(e);
		}
		return Response.ok(list).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@Context UriInfo info) {
		Map<String, String> map = Util.parseFilter(info.getQueryParameters());
		taskRB.deletar(map);
		return Response.ok().build();
	}
	

}
