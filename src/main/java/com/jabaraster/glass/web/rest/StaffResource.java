/**
 * 
 */
package com.jabaraster.glass.web.rest;

import jabara.general.ArgUtil;
import jabara.general.NotFound;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jabaraster.glass.model.CarNumber;
import com.jabaraster.glass.model.StaffLookedCustomer;
import com.jabaraster.glass.service.IStaffLookedCustomerService;

/**
 * @author jabaraster
 */
@Path("staff")
public class StaffResource {

    private final IStaffLookedCustomerService staffLookedCustomerService;

    /**
     * @param pStaffLookedCustomerService -
     */
    @Inject
    public StaffResource(final IStaffLookedCustomerService pStaffLookedCustomerService) {
        this.staffLookedCustomerService = ArgUtil.checkNull(pStaffLookedCustomerService, "pStaffLookedCustomerService"); //$NON-NLS-1$

    }

    /**
     * @param pRequestSs -
     * @param pRequestStaffName -
     * @return -
     */
    public List<StaffLookedCustomer> getLookedCustomers( //
            @QueryParam("ss") final String pRequestSs //
            , @QueryParam("self") final String pRequestStaffName) {
        if (pRequestSs == null || pRequestSs.trim().length() == 0) {
            return Collections.emptyList();
        }
        return this.staffLookedCustomerService.getLookedCustomers(pRequestSs, pRequestStaffName);
    }

    /**
     * @param pStaffName -
     * @param pSsDescriptor -
     * @param pCarDepartment -
     * @param pCarClass -
     * @param pCarCategory -
     * @param pCarNumber -
     * @return -
     */
    @Path("look")
    @POST
    public Response postLooking( //
            @FormParam("staffName") final String pStaffName //
            , @FormParam("ssDescriptor") final String pSsDescriptor //
            , @FormParam("carDepartment") final String pCarDepartment //
            , @FormParam("carClass") final String pCarClass //
            , @FormParam("carCategory") final String pCarCategory //
            , @FormParam("carNumber") final String pCarNumber //
    ) {
        final CarNumber carNumber = new CarNumber();
        carNumber.setCategory(pCarCategory);
        carNumber.setClassification(pCarClass);
        carNumber.setDepartment(pCarDepartment);
        carNumber.setNumber(pCarNumber);
        try {
            this.staffLookedCustomerService.setStaffLookedCustomer(pStaffName, pSsDescriptor, carNumber);
            return Response.status(Status.CREATED).build();
        } catch (final NotFound e) {
            return Response.status(Status.NO_CONTENT).build();
        }
    }
}
