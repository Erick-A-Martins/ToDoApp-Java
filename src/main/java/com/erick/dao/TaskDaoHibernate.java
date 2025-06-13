package com.erick.dao;

import com.erick.model.Task;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TaskDaoHibernate implements TaskDao{

    private final SessionFactory sessionFactory;

    public TaskDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer addTask(Task task) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(task);
            tx.commit();
            return task.getId();
        }
    }

    @Override
    public boolean removeTaskById(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Task task = session.find(Task.class, id);
            if(task != null) {
                session.remove(task);
                tx.commit();
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean updateTask(Task task) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(task);
            tx.commit();
            return true;
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Task.class, id);
        }
    }

    @Override
    public List<Task> listAllTasks() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from Task", Task.class).getResultList();
        }
    }
}
