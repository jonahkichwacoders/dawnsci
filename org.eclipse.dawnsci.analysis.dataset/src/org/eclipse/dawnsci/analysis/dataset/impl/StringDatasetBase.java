/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

// This is generated from DoubleDataset.java by fromdouble.py

package org.eclipse.dawnsci.analysis.dataset.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;


/**
 * Extend dataset for String values // PRIM_TYPE
 */
public class StringDatasetBase extends AbstractDataset {
	// pin UID to base class
	private static final long serialVersionUID = Dataset.serialVersionUID;

	protected String[] data; // subclass alias // PRIM_TYPE

	@Override
	protected void setData() {
		data = (String[]) odata; // PRIM_TYPE
	}

	protected static String[] createArray(final int size) { // PRIM_TYPE
		String[] array = null; // PRIM_TYPE

		try {
			array = new String[size]; // PRIM_TYPE
		} catch (OutOfMemoryError e) {
			logger.error("The size of the dataset ({}) that is being created is too large "
					+ "and there is not enough memory to hold it.", size);
			throw new OutOfMemoryError("The dimensions given are too large, and there is "
					+ "not enough memory available in the Java Virtual Machine");
		}
		return array;
	}

	@Override
	public int getDtype() {
		return STRING; // DATA_TYPE
	}

	public StringDatasetBase() {
	}

	/**
	 * Create a zero-filled dataset of given shape
	 * @param shape
	 */
	public StringDatasetBase(final int... shape) {
		if (shape.length == 1) {
			size = shape[0];
			if (size < 0) {
				throw new IllegalArgumentException("Negative component in shape is not allowed");
			}
		} else {
			size = calcSize(shape);
		}
		this.shape = shape.clone();

		odata = data = createArray(size);
	}

	/**
	 * Create a dataset using given data
	 * @param data
	 * @param shape
	 *            (can be null to create 1D dataset)
	 */
	public StringDatasetBase(final String[] data, int... shape) { // PRIM_TYPE
		if (data == null) {
			throw new IllegalArgumentException("Data must not be null");
		}
		if (shape == null || shape.length == 0) {
			shape = new int[] { data.length };
		}
		size = calcSize(shape);
		if (size != data.length) {
			throw new IllegalArgumentException(String.format("Shape %s is not compatible with size of data array, %d",
					Arrays.toString(shape), data.length));
		}
		this.shape = shape.clone();

		odata = this.data = data;
	}

	/**
	 * Copy a dataset
	 * @param dataset
	 */
	public StringDatasetBase(final StringDatasetBase dataset) {
		copyToView(dataset, this, true, true);
		if (dataset.stride == null) {
			odata = data = dataset.data.clone();
		} else {
			offset = 0;
			stride = null;
			base = null;
			odata = data = createArray(size);
			IndexIterator iter = dataset.getIterator();
			for (int i = 0; iter.hasNext(); i++) {
				data[i] = dataset.data[iter.index];
			}
		}
	}

	/**
	 * Cast a dataset to this class type
	 * @param dataset
	 */
	public StringDatasetBase(final Dataset dataset) {
		copyToView(dataset, this, true, false);
		offset = 0;
		stride = null;
		base = null;
		odata = data = createArray(size);
		IndexIterator iter = dataset.getIterator();
		for (int i = 0; iter.hasNext(); i++) {
			data[i] = dataset.getStringAbs(iter.index); // GET_ELEMENT_WITH_CAST
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (getRank() == 0) // already true for zero-rank dataset
			return true;

		StringDatasetBase other = (StringDatasetBase) obj;
		IndexIterator iter = getIterator();
		IndexIterator oiter = other.getIterator();
		while (iter.hasNext() && oiter.hasNext()) {
			if (!data[iter.index].equals(other.data[oiter.index])) // OBJECT_UNEQUAL
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public StringDatasetBase clone() {
		return new StringDatasetBase(this);
	}

	/**
	 * Create a dataset from an object which could be a Java list, array (of arrays...) or Number. Ragged
	 * sequences or arrays are padded with zeros.
	 *
	 * @param obj
	 * @return dataset with contents given by input
	 */
	public static StringDatasetBase createFromObject(final Object obj) {
		StringDatasetBase result = new StringDatasetBase();

		result.shape = getShapeFromObject(obj);
		result.size = calcSize(result.shape);

		result.odata = result.data = createArray(result.size);

		int[] pos = new int[result.shape.length];
		result.fillData(obj, 0, pos);
		return result;
	}

	/**
	 * @param shape
	 * @return a dataset filled with ones
	 */
	public static StringDatasetBase ones(final int... shape) {
		return new StringDatasetBase(shape).fill(1);
	}

	@Override
	public StringDatasetBase fill(final Object obj) {
		String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT
		IndexIterator iter = getIterator();
		while (iter.hasNext()) {
			data[iter.index] = dv;
		}

		setDirty();
		return this;
	}

	/**
	 * This is a typed version of {@link #getBuffer()}
	 * @return data buffer as linear array
	 */
	public String[] getData() { // PRIM_TYPE
		return data;
	}

	@Override
	protected int getBufferLength() {
		if (data == null)
			return 0;
		return data.length;
	}

	@Override
	public StringDatasetBase getView() {
		StringDatasetBase view = new StringDatasetBase();
		copyToView(this, view, true, true);
		view.setData();
		return view;
	}

	/**
	 * Get a value from an absolute index of the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @return value
	 */
	public String getAbs(final int index) { // PRIM_TYPE
		return data[index];
	}

	@Override
	public boolean getElementBooleanAbs(final int index) {
		return false;
	}

	@Override
	public double getElementDoubleAbs(final int index) {
		return 0;
	}

	@Override
	public long getElementLongAbs(final int index) {
		return 0;
	}

	@Override
	public Object getObjectAbs(final int index) {
		return data[index];
	}

	@Override
	public String getStringAbs(final int index) {
		return stringFormat == null ?  data[index] : // FORMAT_STRING
			stringFormat.format(data[index]);
	}

	/**
	 * Set a value at absolute index in the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @param val
	 *            new value
	 */
	public void setAbs(final int index, final String val) { // PRIM_TYPE
		data[index] = val;
		setDirty();
	}

	@Override
	protected void setItemDirect(final int dindex, final int sindex, final Object src) {
		String[] dsrc = (String[]) src; // PRIM_TYPE
		data[dindex] = dsrc[sindex];
	}

	@Override
	public void setObjectAbs(final int index, final Object obj) {
		if (index < 0 || index > data.length) {
			throw new IndexOutOfBoundsException("Index given is outside dataset");
		}

		setAbs(index, obj.toString()); // FROM_OBJECT
	}

	/**
	 * @param i
	 * @return item in given position
	 */
	public String get(final int i) { // PRIM_TYPE
		return data[get1DIndex(i)];
	}

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public String get(final int i, final int j) { // PRIM_TYPE
		return data[get1DIndex(i, j)];
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public String get(final int... pos) { // PRIM_TYPE
		return data[get1DIndex(pos)];
	}

	@Override
	public Object getObject(final int i) {
		return get(i); // CLASS_TYPE
	}

	@Override
	public Object getObject(final int i, final int j) {
		return get(i, j); // CLASS_TYPE
	}

	@Override
	public Object getObject(final int... pos) {
		return get(pos); // CLASS_TYPE
	}

	@Override
	public String getString(final int i) {
		return getStringAbs(get1DIndex(i));
	}

	@Override
	public String getString(final int i, final int j) {
		return getStringAbs(get1DIndex(i, j));
	}

	@Override
	public String getString(final int... pos) {
		return getStringAbs(get1DIndex(pos));
	}

	@Override
	public double getDouble(final int i) {
		return 0;
	}

	@Override
	public double getDouble(final int i, final int j) {
		return 0;
	}

	@Override
	public double getDouble(final int... pos) {
		return 0;
	}

	@Override
	public float getFloat(final int i) {
		return 0;
	}

	@Override
	public float getFloat(final int i, final int j) {
		return 0;
	}

	@Override
	public float getFloat(final int... pos) {
		return 0;
	}

	@Override
	public long getLong(final int i) {
		return 0;
	}

	@Override
	public long getLong(final int i, final int j) {
		return 0;
	}

	@Override
	public long getLong(final int... pos) {
		return 0;
	}

	@Override
	public int getInt(final int i) {
		return 0;
	}

	@Override
	public int getInt(final int i, final int j) {
		return 0;
	}

	@Override
	public int getInt(final int... pos) {
		return 0;
	}

	@Override
	public short getShort(final int i) {
		return 0;
	}

	@Override
	public short getShort(final int i, final int j) {
		return 0;
	}

	@Override
	public short getShort(final int... pos) {
		return 0;
	}

	@Override
	public byte getByte(final int i) {
		return 0;
	}

	@Override
	public byte getByte(final int i, final int j) {
		return 0;
	}

	@Override
	public byte getByte(final int... pos) {
		return 0;
	}

	@Override
	public boolean getBoolean(final int i) {
		return false;
	}

	@Override
	public boolean getBoolean(final int i, final int j) {
		return false;
	}

	@Override
	public boolean getBoolean(final int... pos) {
		return false;
	}

	/**
	 * Sets the value at a particular point to the passed value. The dataset must be 1D
	 *
	 * @param value
	 * @param i
	 */
	public void setItem(final String value, final int i) { // PRIM_TYPE
		setAbs(get1DIndex(i), value);
	}

	/**
	 * Sets the value at a particular point to the passed value. The dataset must be 2D
	 *
	 * @param value
	 * @param i
	 * @param j
	 */
	public void setItem(final String value, final int i, final int j) { // PRIM_TYPE
		setAbs(get1DIndex(i, j), value);
	}

	/**
	 * Sets the value at a particular point to the passed value
	 *
	 * @param value
	 * @param pos
	 */
	public void setItem(final String value, final int... pos) { // PRIM_TYPE
		setAbs(get1DIndex(pos), value);
	}

	@Override
	public void set(final Object obj, final int i) {
		setItem(obj.toString(), i); // FROM_OBJECT
	}

	@Override
	public void set(final Object obj, final int i, final int j) {
		setItem(obj.toString(), i, j); // FROM_OBJECT
	}

	@Override
	public void set(final Object obj, int... pos) {
		if (pos == null || (pos.length == 0 && shape.length > 0)) {
			pos = new int[shape.length];
		}

		setItem(obj.toString(), pos); // FROM_OBJECT
	}


	@Override
	public void resize(int... newShape) {
		final IndexIterator iter = getIterator();
		final int nsize = calcSize(newShape);
		final String[] ndata = createArray(nsize); // PRIM_TYPE
		for (int i = 0; iter.hasNext() && i < nsize; i++) {
			ndata[i] = data[iter.index];
		}

		odata = data = ndata;
		size = nsize;
		shape = newShape;
		stride = null;
		offset = 0;
		base = null;
	}

	@Override
	public StringDatasetBase sort(Integer axis) {
		if (axis == null) {
			Arrays.sort(data);
		} else {
			axis = checkAxis(axis);
			
			StringDatasetBase ads = new StringDatasetBase(shape[axis]);
			PositionIterator pi = getPositionIterator(axis);
			int[] pos = pi.getPos();
			boolean[] hit = pi.getOmit();
			while (pi.hasNext()) {
				copyItemsFromAxes(pos, hit, ads);
				Arrays.sort(ads.data);
				setItemsOnAxes(pos, hit, ads.data);
			}
		}
		
		setDirty();
		return this;
		// throw new UnsupportedOperationException("Cannot sort dataset"); // BOOLEAN_USE
	}

	@Override
	public StringDatasetBase getUniqueItems() {
		Set<String> set = new TreeSet<String>(); // CLASS_TYPE
		IndexIterator it = getIterator();
		while (it.hasNext()) {
			set.add(data[it.index]);
		}

		StringDataset u = new StringDataset(set.size()); // CLASS_TYPE
		int i = 0;
		String[] udata = u.getData(); // PRIM_TYPE
		for (String v : set) { // CLASS_TYPE
			udata[i++] = v;
		}
		return u;
	}

	@Override
	public StringDatasetBase getSlice(final SliceIterator siter) {
		StringDatasetBase result = new StringDatasetBase(siter.getShape());
		String[] rdata = result.data; // PRIM_TYPE

		for (int i = 0; siter.hasNext(); i++)
			rdata[i] = data[siter.index];

		result.setName(name + BLOCK_OPEN + Slice.createString(siter.shape, siter.start, siter.stop, siter.step) + BLOCK_CLOSE);
		return result;
	}

	@Override
	public void fillDataset(Dataset result, IndexIterator iter) {
		IndexIterator riter = result.getIterator();

		String[] rdata = ((StringDatasetBase) result).data; // PRIM_TYPE

		while (riter.hasNext() && iter.hasNext())
			rdata[riter.index] = data[iter.index];
	}

	@Override
	public StringDatasetBase setByBoolean(final Object obj, Dataset selection) {
		if (obj instanceof Dataset) {
			final Dataset ds = (Dataset) obj;
			final int length = ((Number) selection.sum()).intValue();
			if (length != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of true items in selection does not match number of items in dataset");
			}

			final IndexIterator oiter = ds.getIterator();
			final BooleanIterator biter = getBooleanIterator(selection);

			while (biter.hasNext() && oiter.hasNext()) {
				data[biter.index] = ds.getStringAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT
			final BooleanIterator biter = getBooleanIterator(selection);

			while (biter.hasNext()) {
				data[biter.index] = dv;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public StringDatasetBase setBy1DIndex(final Object obj, final Dataset index) {
		if (obj instanceof Dataset) {
			final Dataset ds = (Dataset) obj;
			if (index.getSize() != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of items in index dataset does not match number of items in dataset");
			}

			final IndexIterator oiter = ds.getIterator();
			final IntegerIterator iter = new IntegerIterator(index, size);

			while (iter.hasNext() && oiter.hasNext()) {
				data[iter.index] = ds.getStringAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT
			IntegerIterator iter = new IntegerIterator(index, size);

			while (iter.hasNext()) {
				data[iter.index] = dv;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public StringDatasetBase setByIndexes(final Object obj, final Object... indexes) {
		final IntegersIterator iter = new IntegersIterator(shape, indexes);
		final int[] pos = iter.getPos();

		if (obj instanceof Dataset) {
			final Dataset ds = (Dataset) obj;
			if (calcSize(iter.getShape()) != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of items in index datasets does not match number of items in dataset");
			}

			final IndexIterator oiter = ds.getIterator();

			while (iter.hasNext() && oiter.hasNext()) {
				setItem(ds.getStringAbs(oiter.index), pos); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT

			while (iter.hasNext()) {
				setItem(dv, pos);
			}
		}
		setDirty();
		return this;
	}

	@Override
	StringDatasetBase setSlicedView(Dataset view, Dataset d) {
		final BroadcastIterator it = BroadcastIterator.createIterator(view, d);

		while (it.hasNext()) {
			data[it.aIndex] = d.getStringAbs(it.bIndex); // GET_ELEMENT_WITH_CAST
		}
		return this;
	}

	@Override
	public StringDatasetBase setSlice(final Object obj, final IndexIterator siter) {

		if (obj instanceof IDataset) {
			final IDataset ds = (IDataset) obj;
			final int[] oshape = ds.getShape();

			if (!areShapesCompatible(siter.getShape(), oshape)) {
				throw new IllegalArgumentException(String.format(
						"Input dataset is not compatible with slice: %s cf %s", Arrays.toString(oshape),
						Arrays.toString(siter.getShape())));
			}

			if (ds instanceof Dataset) {
				final Dataset ads = (Dataset) ds;
				final IndexIterator oiter = ads.getIterator();

				while (siter.hasNext() && oiter.hasNext())
					data[siter.index] = ads.getStringAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			} else {
				final IndexIterator oiter = new PositionIterator(oshape);
				final int[] pos = oiter.getPos();

				while (siter.hasNext() && oiter.hasNext())
					data[siter.index] = ds.getString(pos); // PRIM_TYPE
			}
		} else {
			try {
				String v = obj.toString(); // PRIM_TYPE // FROM_OBJECT

				while (siter.hasNext())
					data[siter.index] = v;
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting slice is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public void copyItemsFromAxes(final int[] pos, final boolean[] axes, final Dataset dest) {
		String[] ddata = (String[]) dest.getBuffer(); // PRIM_TYPE

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);
		int[] sshape = squeezeShape(siter.getShape(), false);

		IndexIterator diter = dest.getSliceIterator(null, sshape, null);

		if (ddata.length < calcSize(sshape)) {
			throw new IllegalArgumentException("destination array is not large enough");
		}

		while (siter.hasNext() && diter.hasNext())
			ddata[diter.index] = data[siter.index];
	}

	@Override
	public void setItemsOnAxes(final int[] pos, final boolean[] axes, final Object src) {
		String[] sdata = (String[]) src; // PRIM_TYPE

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);

		if (sdata.length < calcSize(siter.getShape())) {
			throw new IllegalArgumentException("destination array is not large enough");
		}

		for (int i = 0; siter.hasNext(); i++) {
			data[siter.index] = sdata[i];
		}
		setDirty();
	}

	@Override
	protected Number fromDoubleToNumber(double x) {
		// return Integer.valueOf((int) (long) x); // BOOLEAN_USE
		return null; // OBJECT_USE
	}

	private List<int[]> findPositions(final String value) { // PRIM_TYPE
		IndexIterator iter = getIterator(true);
		List<int[]> posns = new ArrayList<int[]>();
		int[] pos = iter.getPos();

		{
			while (iter.hasNext()) {
				if (data[iter.index] == value) {
					posns.add(pos.clone());
				}
			}
		}
		return posns;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public int[] maxPos(boolean ignoreInvalids) {
		if (storedValues == null || storedValues.isEmpty()) {
			calculateMaxMin(ignoreInvalids, ignoreInvalids);
		}
		String n = storeName(ignoreInvalids, ignoreInvalids, STORE_MAX_POS);
		Object o = storedValues.get(n);

		List<int[]> max = null;
		if (o == null) {
			// max = findPositions(max(false).intValue() != 0); // BOOLEAN_USE
			max = findPositions(null); // OBJECT_USE
			storedValues.put(n, max);
		} else if (o instanceof List<?>) {
			max = (List<int[]>) o;
		} else {
			throw new InternalError("Inconsistent internal state of stored values for statistics calculation");
		}

		return max.get(0); // first maximum
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public int[] minPos(boolean ignoreInvalids) {
		if (storedValues == null || storedValues.isEmpty()) {
			calculateMaxMin(ignoreInvalids, ignoreInvalids);
		}
		String n = storeName(ignoreInvalids, ignoreInvalids, STORE_MIN_POS);
		Object o = storedValues.get(n);
		List<int[]> min = null;
		if (o == null) {
			// min = findPositions(min(false).intValue() != 0); // BOOLEAN_USE
			min = findPositions(null); // OBJECT_USE
			storedValues.put(n, min);
		} else if (o instanceof List<?>) {
			min = (List<int[]>) o;
		} else {
			throw new InternalError("Inconsistent internal state of stored values for statistics calculation");
		}

		return min.get(0); // first minimum
	}

	@Override
	public boolean containsNans() {
		return false;
	}

	@Override
	public boolean containsInfs() {
		return false;
	}

	@Override
	public boolean containsInvalidNumbers() {
		return false;
	}

	@Override
	public StringDatasetBase iadd(final Object b) {
		return this;
	}

	@Override
	public StringDatasetBase isubtract(final Object b) {
		return this;
	}

	@Override
	public StringDatasetBase imultiply(final Object b) {
		return this;
	}

	@Override
	public StringDatasetBase idivide(final Object b) {
		return this;
	}

	@Override
	public StringDatasetBase ifloor() {
		return this;
	}

	@Override
	public StringDatasetBase iremainder(final Object b) {
		return this;
	}

	@Override
	public StringDatasetBase ipower(final Object b) {
		return this;
	}

	@Override
	public double residual(final Object b, final Dataset w, boolean ignoreNaNs) {
		double sum = 0;
		return sum;
	}
}
