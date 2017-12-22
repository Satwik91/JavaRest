package test;

//1. Implement a service that tracks referrers to a website and their counts by domain using 
//programming languages and tools of your choice.
// -
// Create an endpoint that accepts a url as a parameter and counts the number of times the d
//omain in the url has been seen.
// - Create an endpoint that returns the 3 highest seen referring domains.
// - Simple UI to display the 3 highest.
//Upload your code to a site like Github and bring a laptop to demo the project.

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Application;

@Path("/hello")
public class Hello 
{

	public static boolean DESC = false;
	static Map<String,Integer> hm = new HashMap<String,Integer>();
	public Map<String, Integer> getHm() {
		return hm;
	}


	public void setHm(Map<String, Integer> hm) {
		this.hm = hm;
	}


	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getReferrer()
	{
		String resource = "<html><body><ul>";
		  Map<String, Integer> sortedMapDesc = sortByComparator(getHm(), DESC);
		  int i =0;
		  
		for(String s:sortedMapDesc.keySet())
		{
			if(i<3)
			{
			resource = resource+"<li>Url: "+s+" Times Visited: "+hm.get(s)+"</li>";
			i++;
			}
					
		}
		resource=resource+"</ul></body></html>";
		System.out.println(resource);
		return resource;
	}
	
	
	 private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
	    {

	        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

	        // Sorting the list based on values
	        Collections.sort(list, new Comparator<Entry<String, Integer>>()
	        {
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2)
	            {
	                if (order)
	                {
	                    return o1.getValue().compareTo(o2.getValue());
	                }
	                else
	                {
	                    return o2.getValue().compareTo(o1.getValue());

	                }
	            }
	        });

	        // Maintaining insertion order with the help of LinkedList
	        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	        for (Entry<String, Integer> entry : list)
	        {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }

	        return sortedMap;
	    }


	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/ref")
	public Response countReferrer(@FormParam("url") String enteredUrl)
	{
		Map<String,Integer> hm1 = getHm();
		if(hm1.containsKey(enteredUrl))
		{
			hm1.put(enteredUrl, hm1.get(enteredUrl)+1);
			setHm(hm1);
		}
		else
		{
			hm1.put(enteredUrl, 1);
		}

		return Response.status(200).build();
	}
	
}
