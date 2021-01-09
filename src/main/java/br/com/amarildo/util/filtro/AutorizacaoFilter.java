package br.com.amarildo.util.filtro;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.amarildo.controller.UsuarioController;


@WebFilter("/sistema/*")
public class AutorizacaoFilter implements Filter {

	@Inject
	private UsuarioController autenticacao;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
	
		boolean loggedIn        = !autenticacao.isLogado(); 
		boolean loginRequest    = !request.getRequestURI().endsWith("/index.xhtml");

		if (loggedIn && loginRequest) {
			response.sendRedirect(request.getContextPath() + "/index.xhtml");
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

}