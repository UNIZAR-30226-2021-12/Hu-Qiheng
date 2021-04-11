package com.unizar.unozar.core.service;

import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.GetAliasRequest;
import com.unizar.unozar.core.controller.resources.RegisterRequest;
import com.unizar.unozar.core.controller.resources.StatisticsRequest;
import com.unizar.unozar.core.controller.resources.UpdateEmailRequest;
import com.unizar.unozar.core.controller.resources.UpdatePasswordRequest;

public interface PlayerService {

  public String createPlayer(RegisterRequest request);

  public String updatePlayerEmail(UpdateEmailRequest request);

  public String updatePlayerPassword(UpdatePasswordRequest request);

  public String deletePlayer(DeletePlayerRequest request);

  public String findByEmail(String email);

  public String authentication(AuthenticationRequest request);

  public String getStatistics(StatisticsRequest request);

  public String getAlias(GetAliasRequest request);



}
