package controllers;



import views.FilterView;

public class FilterViewController {
	FilterView filterView;
	public FilterViewController(FilterView filterView) {
		this.filterView=filterView;
		this.filterView.getBuscar().addActionListener(e -> {
			
		});
	}
	public void filtrar() {
		
	}
}
