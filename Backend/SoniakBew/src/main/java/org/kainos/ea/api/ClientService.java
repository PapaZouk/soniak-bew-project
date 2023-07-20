package org.kainos.ea.api;

import org.kainos.ea.cli.Client;
import org.kainos.ea.cli.RequestClientWithMaxValue;
import org.kainos.ea.db.ClientDao;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientDao clientDao = new ClientDao();

    public List<Client> getAllClients() throws SQLException {
        List<Client> clientList = clientDao.getAllClients();

        return clientList;
    }

    public RequestClientWithMaxValue getClientWithMaxValue() throws SQLException {
        RequestClientWithMaxValue clientMaxValue = clientDao.getClientWithMaxValue();

        return clientMaxValue;
    }
}