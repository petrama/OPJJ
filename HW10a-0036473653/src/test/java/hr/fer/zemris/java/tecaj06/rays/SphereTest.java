package hr.fer.zemris.java.tecaj06.rays;

import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.Sphere;

import org.junit.Assert;
import org.junit.Test;

public class SphereTest {
	@Test
	public void dvaPresjekaTest() {
		Sphere sfera = new Sphere(new Point3D(5, 5, 5), 1, 1, 1, 1,1,
				1,1, 1);
		
		Ray ray=Ray.fromPoints(new Point3D(-1,-1,-1), new Point3D(6,6,6));
		RayIntersection in=sfera.findClosestRayIntersection(ray);
		Assert.assertTrue("x of intersection expected to be:4.422649730810374",in.getPoint().x==4.422649730810374);
		Assert.assertTrue("y of intersection expected to be:4.422649730810374",in.getPoint().y==4.422649730810374);
		Assert.assertTrue("z of intersection expected to be:4.422649730810374",in.getPoint().z==4.422649730810374);
	
	
	
	}
	  @Test
	 public void NormalGetterTest() {
	  Sphere sphere = new Sphere(new Point3D(0, 0, 0), 5, 0.5, 0.5, 0.5, 0.5,
	    0.5, 0.5, 0.5);
	  // zraka koja tangira sferu
	  Ray ray = Ray.fromPoints(new Point3D(0, -2, 5), new Point3D(0, 2, 5));
	  RayIntersection intersection = sphere.findClosestRayIntersection(ray);
//	  System.out.println(intersection.getPoint().x);
//	  System.out.println(intersection.getPoint().y);
//	  System.out.println(intersection.getPoint().z);
	  Point3D normal = intersection.getNormal();
	  System.out.println(normal);
	  System.out.println(normal.z);
	  Assert.assertEquals("Smjer x normale mora biti 0!", 0.0, normal.x, 1E-8);
	  Assert.assertEquals("Smjer y normale mora biti 0!", 0.0, normal.y, 1E-7);
	  Assert.assertEquals("Smjer z tocke presjecista mora biti 1!", 1.0, normal.z,
	    1E-7);
	 }
	
	@Test
	public void dvaPresjecistaSuIzaSfere() {
		Sphere sfera = new Sphere(new Point3D(5, 5, 5), 1, 1, 1, 1,1,
				1,1, 1);
		
		Ray ray=Ray.fromPoints(new Point3D(0,0,0), new Point3D(-6,-6,-6));
		RayIntersection in=sfera.findClosestRayIntersection(ray);
		Assert.assertTrue("There should be no intersection",in==null);
		

	}
	
	@Test
	public void jednaTockaPresjekaZrakeISfere(){
		Sphere sfera=new Sphere(new Point3D(0, 0, 3), 3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
		Ray zraka=Ray.fromPoints(new Point3D(4, 0, 0), new Point3D(0, 0, 0));
		
		RayIntersection in=sfera.findClosestRayIntersection(zraka);
		Assert.assertTrue("Točka nije točna", in.getDistance()==4.0);
		Assert.assertTrue("x of intersection expected to be:0",in.getPoint().x==0);
		Assert.assertTrue("y of intersection expected to be:0",in.getPoint().y==0);
		Assert.assertTrue("z of intersection expected to be:0",in.getPoint().z==0);
	}
	
	
	@Test
	public void nemaTockaPresjekaZrakeISfere(){
		Sphere sfera=new Sphere(new Point3D(0, 0, 4), 3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
		Ray zraka=Ray.fromPoints(new Point3D(4, 0, 0), new Point3D(0, 0, 0));
		RayIntersection ins=sfera.findClosestRayIntersection(zraka);
		Assert.assertTrue("There should be no intersection!",ins==null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testiranjeZrakeKojaDoticeKugluUNegativnomSmjeru(){
		Sphere sfera=new Sphere(new Point3D(0, 0, 3), 3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
		Ray zraka=Ray.fromPoints(new Point3D(4, 0, 0), new Point3D(7, 0, 0));
		RayIntersection tockapresjeka=sfera.findClosestRayIntersection(zraka);
		Assert.assertTrue("Null expected!", tockapresjeka.getDistance()==4.0);
	}
	

	@Test
	public void testiranjeZrakeKojaSenalaziUnutarKugleIIdeBlizemKraju(){
		Sphere sfera=new Sphere(new Point3D(0, 0, 0), 3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
		Ray zraka=Ray.fromPoints(new Point3D(2, 0, 0), new Point3D(7, 0, 0));
		
		RayIntersection in=sfera.findClosestRayIntersection(zraka);
		Assert.assertTrue("x of intersection expected to be:3",in.getPoint().x==3);
		Assert.assertTrue("y of intersection expected to be:0",in.getPoint().y==0);
		Assert.assertTrue("z of intersection expected to be:0",in.getPoint().z==0);
	}

}
