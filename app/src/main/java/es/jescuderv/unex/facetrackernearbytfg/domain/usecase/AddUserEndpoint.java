package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.ConnectionsDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.ConnectionsRepository;

@Singleton
public class AddUserEndpoint {

    private ConnectionsDataSource mRepository;

    @Inject
    AddUserEndpoint(ConnectionsRepository userRepository) {
        mRepository = userRepository;
    }

    public void execute(String userEndpoint) {
        mRepository.addUserEndpoint(userEndpoint);
    }
}
