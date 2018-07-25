package br.com.supero.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import com.google.common.reflect.TypeToken;



/**
 * Essa classe funciona como um Facade para a utilizacao da JPA na leitura e
 * gerenciamento de objetos gravados no banco de dados.
 *
 * @author Jackson Zampiva
 *
 * @param <ENTITY>
 *            o tipo da entidade persistente
 * @param <ID>
 *            o tipo do identificador da entidade
 */
public abstract class BaseRepository<ENTITY, ID> implements Serializable {

	private static final long serialVersionUID = 8014584798260173413L;
	/**
	 * O EntityManager injetado pelo CDI.
	 */
	@Inject
	protected EntityManager entityManager;
	@Inject
	protected Logger logger;


	/**
	 * O tipo de objeto gerenciado por esse repositorio. Inicializado com o tipo
	 * generico da subclasse.
	 */
	protected final Class<ENTITY> entityClass;

	/**
	 * O tipo do ID do objeto gerenciado por esse repositorio. Inicializado com
	 * o tipo generico da subclasse.
	 */
	protected final Class<ID> idClass;

	/**
	 * Construtor padrao. Obtem os tipos genericos declarados nas subclasses,
	 * para inicializar os campos {@link #entityClass} e {@link #idClass}
	 */
	@SuppressWarnings("unchecked")
	protected BaseRepository() {
		TypeToken<?> typeToken = TypeToken.of(getClass()).getSupertype(BaseRepository.class);
		ParameterizedType type = (ParameterizedType) typeToken.getType();
		Type[] typeArguments = type.getActualTypeArguments();
		this.entityClass = (Class<ENTITY>) typeArguments[0];
		this.idClass = (Class<ID>) typeArguments[1];
	}

	/**
	 * Encontra a entidade pelo id.
	 *
	 * @param id
	 *            o identificador
	 * @return a entidade ou null se nao for encontrada
	 */
	public ENTITY find(ID id) {
		return entityManager.find(entityClass, id);
	}

	/**
	 * Encontra uma entidade (do tipo especificado) pelo id.
	 *
	 * @param entityClass
	 *            o tipo da entidade
	 * @param id
	 *            o identificador
	 * @return a entidade ou null se nao for encontrada
	 */
	public <E> E find(Class<E> entityClass, Object id) {
		return entityManager.find(entityClass, id);
	}

	/**
	 * Lista todas as entidades do mesmo tipo sem ordenacao ou criterio de
	 * busca.
	 *
	 * @return a lista de entidades
	 */
	public List<ENTITY> list() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ENTITY> cq = cb.createQuery(entityClass);
		cq.select(cq.from(entityClass));
		return entityManager.createQuery(cq).getResultList();
	}

	/**
	 * Inicia uma transacao (caso nao exista) e descarrega  o cache do
	 * contexto de persistencia (operacoes pendentes do EntityManager) para o
	 * banco de dados.
	 * <p>
	 * Em outras palavras, esse metodo envia as instrucoes de
	 * insert/update/delete para o banco de dados.
	 */
	@Transactional
	public void flush() {
		entityManager.joinTransaction();
		entityManager.flush();
	}

	/**
	 * Coloca o objeto no contexto de persistencia, permitindo que alteracoes
	 * nos valores sejam colocadas em cache e enviadas para o banco de dados ao
	 * entrar em uma transacao. Permite tambem que metodos com carregamento LAZY
	 * sejam chamados no objeto.
	 *
	 * @param entity
	 *            a entidade persistente (em estado "new" ou "detached")
	 * @return o objeto gerenciado pelo contexto de persistencia
	 * @see #flush()
	 * @see #detach(Object)
	 */
	public <E> E merge(E entity) {
		return entityManager.merge(entity);
	}

	/**
	 * Marca um objeto para remocao do banco de dados.
	 *
	 * @param entity
	 *            o objeto que se deseja apagar
	 * @see #flush()
	 */
	public void remove(Object entity) {
		entityManager.remove(entityManager.merge(entity));
	}

	/**
	 * Retira um objeto do contexto de persistencia.
	 * <p>
	 * Esse metodo eh util quando se deseja enviar para o banco as alteracoes
	 * feitas em outros objetos, exceto esse.
	 *
	 * @param entity
	 *            a entidade para retirar do contexto
	 * @see #merge(Object)
	 */
	public void detach(Object entity) {
		entityManager.detach(entity);
	}

	/**
	 * Reverte os dados do objeto com os valores atuais persistidos no banco de
	 * dados. (Utilize sempre o objeto retornado)
	 *
	 * @param entity
	 *            a entidade a ser revertida
	 * @return a entidade gerenciada pelo contexto de persistencia, com os
	 *         valores revertidos
	 */
	public <E> E refresh(E entity) {
		entity = entityManager.merge(entity);
		entityManager.refresh(entity);
		return entity;
	}

	
	public void clear() {
		entityManager.clear();
	}


}
