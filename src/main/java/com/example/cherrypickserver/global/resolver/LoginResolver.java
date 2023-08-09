package com.example.cherrypickserver.global.resolver;

import com.example.cherrypickserver.global.utils.TokenUtils;
import com.example.cherrypickserver.member.exception.AuthAnnotationIsNowhereException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginResolver implements HandlerMethodArgumentResolver {

  private final TokenUtils tokenUtils;
  private final Environment env;

  @Override
  public boolean supportsParameter(MethodParameter parameter)
  {
    return parameter.hasParameterAnnotation(IsLogin.class)
            &&
            LoginStatus.class.equals(parameter.getParameterType());
  }

  @Nullable
  @Override
  public Object resolveArgument(@NotNull MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                @NotNull NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception
  {
    Auth auth = parameter.getMethodAnnotation(Auth.class);

    if (auth == null)
      throw new AuthAnnotationIsNowhereException();

    String accessToken = webRequest.getHeader(Objects.requireNonNull(env.getProperty("jwt.auth-header")));
    if(accessToken == null || !tokenUtils.isValidToken(tokenUtils.parseJustTokenFromFullToken(accessToken)))
      return LoginStatus.getNotLoginStatus();

    Long memberId = Long.valueOf(tokenUtils.getUserIdFromFullToken(accessToken));

    if (!auth.optional() && memberId == null) {
      return LoginStatus.getNotLoginStatus();
    }

    return LoginStatus.builder().isLogin(true).memberId(memberId).build();
  }
}

