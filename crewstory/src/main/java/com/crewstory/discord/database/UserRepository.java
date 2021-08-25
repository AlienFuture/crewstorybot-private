package com.crewstory.discord.database;

import com.crewstory.discord.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String PERSISTENCE_UNIT_NAME = "discordBot";
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public void create(User user) {
        EntityManager em = FACTORY.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    public void update(User user) {
        EntityManager em = FACTORY.createEntityManager();
        em.merge(user);
    }

    public List<User> selectAll() {
        EntityManager em = FACTORY.createEntityManager();
        final String QUERY = "SELECT n FROM news n";
        TypedQuery<User> q = em.createQuery(QUERY, User.class);
        List<User> users = new ArrayList<User>();
        try {
            users = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return users;

    }
}
