package easybusmedellin.Servlets;


import easybusmedellin.Model.RutaFinal;
import easybusmedellin.Model.Transporte;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import easybusmedellin.controller.BusquedaSolucion;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;


@WebServlet(name = "EjecutarServlet", urlPatterns = {"/EjecutarServlet"})
public class Servlet extends HttpServlet {

    /**
     * Recibe la peticion de ejecucion y manda el JSon.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            BusquedaSolucion busquedaSolucion = new BusquedaSolucion();
            double latIni,longIni,latDest,longDest;
            int rango;
            latIni = Double.parseDouble(request.getParameter("LatI"));
            longIni = Double.parseDouble(request.getParameter("LongI"));
            latDest = Double.parseDouble(request.getParameter("LatD"));
            longDest = Double.parseDouble(request.getParameter("LongD"));
            rango = Integer.parseInt(request.getParameter("Rango"));
            LinkedList<RutaFinal> solucion = busquedaSolucion.hallarRutas(latIni, longIni, latDest, longDest,rango).getRutaSolucion();
            RutaFinal rutaFinal ;
            Transporte transporte; 
          
            JSONObject JSolucion = new JSONObject();
            JSONObject JTempSolve,JTempRuta;
            //JSolucion.put("solucion", solucion);
            for(int k = 0; k < solucion.size(); k++)
            {
                rutaFinal = solucion.get(k);
                JTempSolve = new JSONObject();
                for(int l = 0;l < rutaFinal.getRutaFinal().size();l++)
                {
                    transporte = rutaFinal.getRutaFinal().get(l);
                    JTempRuta = new JSONObject();
                    JTempRuta.put("Nombre",transporte.getNombre());
                    JTempRuta.put("Precio",transporte.getPrecio());
                    JTempRuta.put("Tipo",transporte.getTipo());
                    if(transporte.getTipo().equals("Metro")){
                        for(int i = 0;i < transporte.getRuta().getCoordenadas().get(0).size();i++)
                        {
                            JTempRuta.accumulate("Coordenada", transporte.getRuta().getCoordenadas().get(1).get(i)+"_"+transporte.getRuta().getCoordenadas().get(0).get(i));
                        }
                        for(int i = 0;i < transporte.getRuta().getCoordenadas().get(0).size();i++)
                        {
                            JTempRuta.accumulate("Estaciones", transporte.getRuta().getEstaciones().get(i));
                        }
                        JTempSolve.accumulate("ruta "+l,JTempRuta);
                    }else{
                        for(int i = 0;i < transporte.getRuta().getCoordenadas().get(0).size();i++)
                        {
                            JTempRuta.accumulate("Coordenada", transporte.getRuta().getCoordenadas().get(1).get(i)+"_"+transporte.getRuta().getCoordenadas().get(0).get(i));
                        }
                        JTempSolve.accumulate("ruta "+l,JTempRuta);
                    }
                }
                JSolucion.accumulate("Respuesta "+k, JTempSolve);
            }

            response.setContentType("application/json");
            response.getWriter().write(JSolucion.toString());
        }catch(JSONException jse) { 
            System.out.println(jse.getMessage());
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
