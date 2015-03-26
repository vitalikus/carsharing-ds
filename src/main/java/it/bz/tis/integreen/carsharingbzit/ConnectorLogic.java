/*
carsharing-ds: car sharing datasource for the integreen cloud

Copyright (C) 2015 TIS Innovation Park - Bolzano/Bozen - Italy

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package it.bz.tis.integreen.carsharingbzit;

import it.bz.tis.integreen.carsharingbzit.api.ApiClient;
import it.bz.tis.integreen.carsharingbzit.api.ListStationsByCityRequest;
import it.bz.tis.integreen.carsharingbzit.api.ListStationsByCityResponse;
import it.bz.tis.integreen.carsharingbzit.api.Station;
import java.io.IOException;
import util.IntegreenException;

/**
 * 
 * @author Davide Montesin <d@vide.bz>
 */
public class ConnectorLogic
{
   static void process(ApiClient apiClient, String[] cityUIDs) throws IOException
   {
      ListStationsByCityRequest request = new ListStationsByCityRequest(cityUIDs);
      ListStationsByCityResponse response = apiClient.callWebService(request, ListStationsByCityResponse.class);
      Station[] stations = response.getCityAndStations()[0].getStation();

      CarSharingXMLRPCPusher xmlrpcPusher = new CarSharingXMLRPCPusher();

      Object result = xmlrpcPusher.syncStations(CarSharingXMLRPCPusher.CARSHARINGSTATION_DATASOURCE, stations);
      if (result instanceof IntegreenException)
      {
         throw new IOException("IntegreenException");
      }

   }
}