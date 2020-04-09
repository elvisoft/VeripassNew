package com.Veripass1;

public class ItemCategoria {
	
	protected long id;
	protected String rutaImagen;
	
	protected String nombre;
	protected String cantidadpass;
	
	
	public ItemCategoria() {
		this.nombre = "";
		this.cantidadpass = "";
		this.rutaImagen = "";
	}
	
	public ItemCategoria(long id, String nombre, String cantidadpass) {
		this.id = id;
		this.nombre = nombre;
		this.cantidadpass = cantidadpass;
		this.rutaImagen = "";
	}
	
	public ItemCategoria(long id, String nombre, String cantidadpass, String rutaImagen) {
		this.id = id;
		this.nombre = nombre;
		this.cantidadpass = cantidadpass;
		this.rutaImagen = rutaImagen;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRutaImagen() {
		return rutaImagen;
	}
	
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getcantidadpass() {
		return cantidadpass;
	}
	
	public void setcantidadpass(String cantidadpass) {
		this.cantidadpass = cantidadpass;
	}


}



