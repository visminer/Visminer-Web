package org.visminer.web.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.visminer.model.MetricValue;

import com.google.gson.stream.JsonWriter;

public class Graphic {
	private String path;

	public Graphic() {

	}

	public Graphic(String path) {
		super();
		this.path = path;
	}

	/**
	 * 
	 * @param chosen
	 *            List of metric values
	 * @return create json file and return String containing value of metric
	 *         value.
	 */
	@SuppressWarnings("unchecked")
	public String generateChart(List<MetricValue> chosen, String relatedto,
			String metric) {
		// Generating JSON file
		JsonWriter writer;
		JSONArray json = new JSONArray();

		try {
			// instance json file
			File f = new File(this.path);
			f.delete();// deleting json file
			// creating new json file
			writer = new JsonWriter(new FileWriter(this.path));

			writer.beginObject(); // {
			writer.name("name").value("class"); // "name" : "class"
			writer.name("children"); // "children" :
			writer.beginArray(); // [
			// IF count elements is equal to ONE the metric is not metric file
			// because of default the visminer store the total value of metric
			if (chosen.size() == 1) {
				MetricValue mv = chosen.get(0);
				writer.beginObject(); // {
				writer.name("name").value(mv.getMetric().getDescription());
				writer.name("size").value(mv.getValue());
				writer.endObject(); // }
				json.add(mv.getValue());
			} else {
				int i = 0;
				int li = 0;
				int li1 = 0;
				String pacoteAtual = "Atual";
				String name = "";
				String pacoteProximo = "Proximo";
				String tag = "";
				for (MetricValue mv : chosen) {
					if (relatedto.equals("file") && mv.getFile() != null) {

						if (metric.equals("LOC") || metric.equals("NOM")
								|| metric.equals("CC")) {

							li = mv.getFile().getPath()
									.lastIndexOf("visminer/") + 9;
							li1 = mv.getFile().getPath().lastIndexOf("/");
							pacoteProximo = mv.getFile().getPath()
									.substring(li, li1);
							if (pacoteProximo.equals(pacoteAtual)) {
								li = mv.getFile().getPath().lastIndexOf("/") + 1;
								name = mv.getFile().getPath().substring(li);
								this.putObject(writer, name, mv.getValue() + "");
								json.add(mv.getValue());
							} else {
								pacoteAtual = pacoteProximo;
								if (i == 0) {
									writer.beginObject(); // {
									writer.name("name").value(pacoteProximo); // "name"
																				// :
																				// "class"
									writer.name("children"); // "children" :
									writer.beginArray(); // [
									i++;
								} else {
									writer.endArray(); // ]
									writer.endObject(); // }
									writer.beginObject(); // {
									writer.name("name").value(pacoteProximo); // "name"
																				// :
																				// "class"
									writer.name("children"); // "children" :
									writer.beginArray(); // [
								}
								li = mv.getFile().getPath().lastIndexOf("/") + 1;
								name = mv.getFile().getPath().substring(li);
								this.putObject(writer, name, mv.getValue() + "");
								json.add(mv.getValue());
							}

						} else {
							li = mv.getFile().getPath().lastIndexOf("/") + 1;
							name = mv.getFile().getPath().substring(li);
							this.putObject(writer, name, mv.getValue() + "");
							json.add(mv.getValue());
						}

					} else if (relatedto.equals("tag") && mv.getTag() != null) {

						li = mv.getTag().getId().getName().lastIndexOf("/") + 1;
						tag = mv.getTag().getId().getName().substring(li);
						if (i == 0) {
							writer.beginObject(); // {
							writer.name("name").value(tag); // "name" : "class"
							writer.name("children"); // "children" :
							writer.beginArray(); // [
							i++;
						} else {
							writer.endArray(); // ]
							writer.endObject(); // }
							writer.beginObject(); // {
							writer.name("name").value(tag); // "name" : "class"
							writer.name("children"); // "children" :
							writer.beginArray(); // [
						}
						name = mv.getTag().getId().getName();
						this.putObject(writer, name, mv.getValue() + "");
						json.add(mv.getValue());
					}
				}
				if (relatedto.equals("tag")){
					i = 0;
					writer.endArray(); // ]
					writer.endObject(); // }
				}else{
					if(metric.equals("LOC") || metric.equals("NOM")
						|| metric.equals("CC")){
					i = 0;
					writer.endArray(); // ]
					writer.endObject(); // }
				}
				}
			}

			writer.endArray(); // ]
			writer.endObject(); // }
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	private void putObject(JsonWriter writer, String name, String value)
			throws IOException {
		writer.beginObject(); // {
		writer.name("name").value(name);
		writer.name("size").value(value);
		writer.endObject(); // }
	}

	public String readJson() throws org.json.simple.parser.ParseException,
			FileNotFoundException, IOException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(this.path));
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject.toJSONString();
	}
}
