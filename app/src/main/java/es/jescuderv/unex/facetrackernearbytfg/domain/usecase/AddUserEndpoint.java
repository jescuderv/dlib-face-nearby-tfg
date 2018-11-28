package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.UserRepository;

@Singleton
public class AddUserEndpoint {

    private UserDataSource mRepository;

    @Inject
    AddUserEndpoint(UserRepository userRepository) {
        mRepository = userRepository;
    }

    public void execute(String userEndpoint) {
        mRepository.addUserEndpoint(userEndpoint);
    }
}
