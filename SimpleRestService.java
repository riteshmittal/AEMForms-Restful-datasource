package com.aem.community.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=SimpleRestService",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=GET",
		ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/techtalkwithritesh/kids" })
public class SimpleRestService extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleRestService.class);

	@Reference
	private ResourceResolverFactory resolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		List<String> list = new ArrayList<>();
		response.setContentType("application/json");

		list.add(addKidObject("Jack", "jack_new@email.com", "01-01-2010", "Male", "Second"));
		list.add(addKidObject("Jill", "jill_new@email.com", "01-01-2010", "Female", "Second"));
		list.add(addKidObject("Bill", "bill_new@email.com", "01-01-2010", "Female", "Second"));

		String finalResponse = processJson(list);
		response.getWriter().write(finalResponse);

	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("inside doPost");

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String grade = request.getParameter("grade");

	}

	private String addKidObject(String name, String email, String dob, String gender, String grade) {
		Gson gson = new Gson();
		return gson.toJson(new Kids(name, email, dob, gender, grade));
	}

	private String processJson(List<String> list) {
		String finalResponse = "[";
		int count = 0;
		for (String kid : list) {
			if (count != 0) {
				finalResponse = finalResponse + "," + kid;
			} else {
				finalResponse = finalResponse + kid;
			}
			count++;
		}

		finalResponse = finalResponse + "]";
		return finalResponse;
	}
}