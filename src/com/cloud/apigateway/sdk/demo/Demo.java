package com.cloud.apigateway.sdk.demo;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;

import com.cloud.sdk.http.HttpMethodName;


public class Demo
{
	//TODO: Replace region with the name of the region in which the service to be accessed is located.
	private static final String region = "";

	//TODO: Replace serviceName with the name of the service you want to access. eg. ecs,vpc,iam,elb
	private static final String serviceName = "";

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		//TODO: Replace the AK and SK with those obtained on the My Credential page.
		String ak = "BXWQNEYIC********HT9PZC";
		String sk = "TBMl5iUOK********D6F5eNl4chh8H";

		//TODO: To specify a project ID (multi-project scenarios), add the X-Project-Id header.
		//TODO: To access a global service, such as IAM, DNS, CDN, and TMS, add the X-Domain-Id header to specify an account ID.
		//TODO: To add a header, find "Add special headers" in the AccessServiceImple.java file.

		//Test the API
		String url = "https://{endpoint}/v1/{project_id}/vpcs";
		get(ak, sk, url);

		//TODO: When creating a VPC, replace {project_id} in postUrl with the actual value.
		//String postUrl = "https://{endpoint}/v1/{project_id}/vpcs";
		//String postbody ="{\"vpc\": {\"name\": \"vpc\",\"cidr\": \"192.168.0.0/16\"}}";
		//post(ak, sk, postUrl, postbody);

		//TODO: When querying a VPC, replace {project_id} in url with the actual value.
		//String url = "https://{endpoint}/v1/{project_id}/vpcs/{vpc_id}";
		//get(ak, sk, url);

		//TODO: When updating a VPC, replace {project_id} and {vpc_id} in putUrl with the actual values.
		//String putUrl = "https://{endpoint}/v1/{project_id}/vpcs/{vpc_id}";
		//String putbody ="{\"vpc\":{\"name\": \"vpc1\",\"cidr\": \"192.168.0.0/16\"}}";
		//put(ak, sk, putUrl, putbody);

		//TODO: When deleting a VPC, replace {project_id} and {vpc_id} in deleteUrl with the actual values.
		//String deleteUrl = "https://{endpoint}/v1/{project_id}/vpcs/{vpc_id}";
		//delete(ak, sk, deleteUrl);
	}

	public static void put(String ak, String sk, String requestUrl,
						   String putBody)
	{

		AccessService accessService = null;
		try
		{
			accessService = new AccessServiceImpl(serviceName, region, ak, sk);
			URL url = new URL(requestUrl);
			HttpMethodName httpMethod = HttpMethodName.PUT;

			InputStream content = new ByteArrayInputStream(putBody.getBytes());
			HttpResponse response = accessService.access(url, content,
					(long) putBody.getBytes().length, httpMethod);

			System.out.println(response.getStatusLine().getStatusCode());


		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			accessService.close();
		}

	}

	public static void patch(String ak, String sk, String requestUrl,
							 String putBody)
	{

		AccessService accessService = null;
		try
		{
			accessService = new AccessServiceImpl(serviceName, region, ak, sk);
			URL url = new URL(requestUrl);
			HttpMethodName httpMethod = HttpMethodName.PATCH;
			InputStream content = new ByteArrayInputStream(putBody.getBytes());
			HttpResponse response = accessService.access(url, content,
					(long) putBody.getBytes().length, httpMethod);

			System.out.println(convertStreamToString(response.getEntity()
					.getContent()));
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			accessService.close();
		}

	}

	public static void delete(String ak, String sk, String requestUrl)
	{

		AccessService accessService = null;

		try
		{
			accessService = new AccessServiceImpl(serviceName, region, ak, sk);
			URL url = new URL(requestUrl);
			HttpMethodName httpMethod = HttpMethodName.DELETE;

			HttpResponse response = accessService.access(url, httpMethod);
			System.out.println(convertStreamToString(response.getEntity()
					.getContent()));
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			accessService.close();
		}

	}

	public static void get(String ak, String sk, String requestUrl)
	{

		AccessService accessService = null;

		try
		{
			accessService = new AccessServiceImpl(serviceName, region, ak, sk);
			URL url = new URL(requestUrl);
			HttpMethodName httpMethod = HttpMethodName.GET;
			HttpResponse response;
			response = accessService.access(url, httpMethod);
			System.out.println(convertStreamToString(response.getEntity()
					.getContent()));
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			accessService.close();
		}

	}

	public static void post(String ak, String sk, String requestUrl,
							String postbody)
	{

		AccessService accessService = new AccessServiceImpl(serviceName,
				region, ak, sk);
		URL url = null;
		try
		{
			url = new URL(requestUrl);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		InputStream content = new ByteArrayInputStream(postbody.getBytes());
		HttpMethodName httpMethod = HttpMethodName.POST;
		HttpResponse response;

		try
		{
			response = accessService.access(url, content,
					(long) postbody.getBytes().length, httpMethod);
			System.out.println(convertStreamToString(response.getEntity()
					.getContent()));
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			accessService.close();
		}
	}

	private static String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

}
